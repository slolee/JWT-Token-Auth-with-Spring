package com.zn.iotproject.service;

import com.zn.iotproject.constant.AuthConstant;
import com.zn.iotproject.domain.Users;
import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.exception.AlreadyExistUserIdException;
import com.zn.iotproject.exception.ExpiredTokenException;
import com.zn.iotproject.exception.InvalidUserException;
import com.zn.iotproject.repository.RefreshTokenRepository;
import com.zn.iotproject.repository.UserRepository;
import com.zn.iotproject.security.JwtDecoder;
import com.zn.iotproject.security.JwtFactory;
import com.zn.iotproject.security.UserContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
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
    private JwtFactory jwtFactory;
    @Autowired
    private JwtDecoder jwtDecoder;

    @Override
    public UserDto.Response join(UserDto.JoinRequest request) {
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
    public AuthDto.Response refresh(AuthDto.RefreshRequest refreshRequest) {
        AuthDto.RefreshKey refreshKey = jwtDecoder.decodeRefreshToken(refreshRequest.getRefreshToken());
        if (!refreshTokenRepository.existsByUserIdAndRefreshKey(refreshKey.getUserId(), refreshKey.getRefreshKey()))
            throw new ExpiredTokenException(String.format("Not found refresh token : [%s]", refreshRequest.getRefreshToken()));
        if (!refreshKey.getUserId().equals(refreshRequest.getUserId()))
            throw new InvalidUserException(String.format("Invalid user : [%s]", refreshRequest.getUserId()));

        Users user = userRepository.findByUserId(refreshRequest.getUserId()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Not found user name : [%s]", refreshRequest.getUserId())));
        UserContext userContext = UserContext.getContextFromUser(user);
        String accessToken = jwtFactory.generateAccessToken(userContext);

        return new AuthDto.Response(accessToken, refreshRequest.getRefreshToken(), AuthConstant.AUTH_TYPE);
    }
}
