package com.zn.iotproject.controller;

import com.zn.iotproject.dto.AuthDto;
import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/overlap")
    public ResponseEntity<AuthDto.CheckOverlapResponse> overlap(@RequestParam String userId) {
        AuthDto.CheckOverlapResponse overlap = authService.checkOverlap(userId);
        return new ResponseEntity<>(overlap, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto.Response> join(@RequestBody UserDto.JoinRequest joinRequest) {
        UserDto.Response response = authService.join(joinRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<AuthDto.Response> refreshToken(@RequestBody AuthDto.RefreshRequest refreshRequest) {
        AuthDto.Response response = authService.refresh(refreshRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
