package com.zn.iotproject.service;

import com.zn.iotproject.domain.Users;
import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.exception.AlreadyExistUserIdException;
import com.zn.iotproject.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto.Response retrieveUser(String userId) {
        Users findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Not Found User : [%s]", userId)));
        return mapper.map(findUser, UserDto.Response.class);
    }

    @Override
    public UserDto.Response join(UserDto.JoinRequest request) {
        if (userRepository.findByUserId(request.getUserId()).isPresent())
            throw new AlreadyExistUserIdException(String.format("This UserId[%s] is Already Exists.", request.getUserId()));

        Users user = mapper.map(request, Users.class);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setJoinDate(new Date());
        Users createdUser = userRepository.save(user);

        return mapper.map(createdUser, UserDto.Response.class);
    }
}
