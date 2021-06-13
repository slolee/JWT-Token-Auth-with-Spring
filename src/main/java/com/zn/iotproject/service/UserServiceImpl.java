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

    @Override
    public UserDto.Response retrieveUser(String userId) {
        Users findUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Not found user : [%s].", userId)));
        return mapper.map(findUser, UserDto.Response.class);
    }
}
