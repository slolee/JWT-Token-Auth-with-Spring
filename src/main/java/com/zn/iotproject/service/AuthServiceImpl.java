package com.zn.iotproject.service;

import com.zn.iotproject.domain.Users;
import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.exception.AlreadyExistUserIdException;
import com.zn.iotproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
}
