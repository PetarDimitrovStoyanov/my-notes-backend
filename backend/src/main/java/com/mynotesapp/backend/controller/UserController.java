package com.mynotesapp.backend.controller;

import com.mynotesapp.backend.domain.service.user.UserService;
import com.mynotesapp.backend.dto.user.LoginDto;
import com.mynotesapp.backend.dto.user.RegisterUserDto;
import com.mynotesapp.backend.dto.user.UserDto;
import com.mynotesapp.backend.util.ControllerApi;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(ControllerApi.BACKEND + ControllerApi.USERS)
public class UserController {

    private final UserService userService;

    @PostMapping(ControllerApi.REGISTER)
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterUserDto registerDto) {
        UserDto user = userService.register(registerDto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping(ControllerApi.LOGIN)
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginDto loginDto) {
        UserDto user = userService.login(loginDto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(ControllerApi.LOGOUT)
    public ResponseEntity<?> logout() {
        userService.logout();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
