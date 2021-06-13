package com.zn.iotproject.controller;

import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto.Response> retrieveUser(@PathVariable String userId) {
        UserDto.Response response = userService.retrieveUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/health_check")
    public ResponseEntity<String> health_check() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
