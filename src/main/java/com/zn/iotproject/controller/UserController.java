package com.zn.iotproject.controller;

import com.zn.iotproject.dto.UserDto;
import com.zn.iotproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto.Response> retrieveUser(@PathVariable String userId) {
        UserDto.Response response = userService.retrieveUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto.Response> join(@RequestBody UserDto.JoinRequest joinRequest) {
        UserDto.Response response = userService.join(joinRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/health_check")
    public String health_check() {
        return "hihihihi";
    }
}
