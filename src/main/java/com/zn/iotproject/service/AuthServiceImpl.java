package com.zn.iotproject.service;

import com.zn.iotproject.constant.AuthConstant;
import com.zn.iotproject.domain.TokenBlackList;
import com.zn.iotproject.domain.Users;
import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.exception.AlreadyExistUserIdException;
import com.zn.iotproject.exception.DiscardedRefreshTokenException;
import com.zn.iotproject.exception.ExpiredTokenException;
import com.zn.iotproject.repository.RefreshTokenRepository;
import com.zn.iotproject.repository.TokenBlackListRepository;
import com.zn.iotproject.repository.UserRepository;
import com.zn.iotproject.security.JwtDecoder;
import com.zn.iotproject.security.JwtFactory;
import com.zn.iotproject.security.UserContext;
import com.zn.iotproject.security.Utils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private TokenBlackListRepository tokenBlackListRepository;
    @Autowired
    private JwtFactory jwtFactory;
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public UserDto.Response signup(UserDto.SignupRequest request) {
        if (userRepository.existsByUserId(request.getUserId()))
            throw new AlreadyExistUserIdException(String.format("This UserId[%s] is already exists.", request.getUserId()));
        Users user = mapper.map(request, Users.class);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setJoinDate(new Date());
        Users createdUser = userRepository.save(user);

        return mapper.map(createdUser, UserDto.Response.class);
    }

    @Override
    public AuthDto.CheckOverlapResponse checkOverlap(String userId) {
        Optional<Users> findUser = userRepository.findByUserId(userId);
        return new AuthDto.CheckOverlapResponse(findUser.isPresent());
    }

    @Override
    public AuthDto.Response refresh(AuthDto.Request refreshRequest) {
        String refreshKey = jwtDecoder.decodeRefreshToken(refreshRequest);
        if (tokenBlackListRepository.existsByToken(refreshRequest.getRefreshToken()))
            throw new DiscardedRefreshTokenException(String.format("This refresh token is discarded : [%s]", refreshRequest.getRefreshToken()));
        if (!refreshTokenRepository.existsByUserIdAndRefreshKey(refreshRequest.getUserId(), refreshKey))
            throw new ExpiredTokenException(String.format("Not found refresh token : [%s]", refreshRequest.getRefreshToken()));

        Users user = userRepository.findByUserId(refreshRequest.getUserId()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Not found user name : [%s]", refreshRequest.getUserId())));
        UserContext userContext = UserContext.getContextFromUser(user);
        String accessToken = jwtFactory.generateAccessToken(userContext);

        tokenBlackListRepository.save(new TokenBlackList(refreshRequest.getAccessToken()));
        return new AuthDto.Response(accessToken, refreshRequest.getRefreshToken(), AuthConstant.AUTH_TYPE);
    }

    @Override
    public void discardToken(String token) {
        if (!Utils.isExpired(token) && !tokenBlackListRepository.existsByToken(token))
            tokenBlackListRepository.save(new TokenBlackList(token));
    }
}
