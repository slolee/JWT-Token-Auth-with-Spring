package com.zn.iotproject.service;

import com.zn.iotproject.dto.UserDto;

public interface UserService {
    UserDto.Response retrieveUser(String userId);

}
