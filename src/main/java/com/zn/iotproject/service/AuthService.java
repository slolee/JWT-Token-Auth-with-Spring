package com.zn.iotproject.service;

import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.dto.UserDto;

public interface AuthService {
    UserDto.Response join(UserDto.JoinRequest request);
    AuthDto.CheckOverlapResponse checkOverlap(String userId);
    AuthDto.Response refresh(AuthDto.RefreshRequest refreshRequest);
}
