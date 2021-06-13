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
    public ResponseEntity<UserDto.Response> signup(@RequestBody UserDto.SignupRequest joinRequest) {
        UserDto.Response response = authService.signup(joinRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/access_token")
    public ResponseEntity<AuthDto.Response> refreshToken(@RequestBody AuthDto.Request requestBody) {
        AuthDto.Response response = authService.refresh(requestBody);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody AuthDto.Request requestBody) {
        authService.discardToken(requestBody.getAccessToken());
        authService.discardToken(requestBody.getRefreshToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
