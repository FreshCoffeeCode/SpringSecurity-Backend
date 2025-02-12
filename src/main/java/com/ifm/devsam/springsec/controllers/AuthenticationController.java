package com.ifm.devsam.springsec.controllers;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.dto.UserLoginDto;
import com.ifm.devsam.springsec.domain.dto.UserRegistrationDto;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.mappers.RegisterDtoToUserEntityMapper;
import com.ifm.devsam.springsec.mappers.UserLoginDtoToUserEntityMapper;
import com.ifm.devsam.springsec.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public String register(@RequestBody UserRegistrationDto registerDto, HttpServletResponse response) {
        UserEntity userToRegister = RegisterDtoToUserEntityMapper.INSTANCE.map(registerDto);
        AuthenticationResponse token = authenticationService.register(userToRegister, response);
        return "Registered Successfully";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody UserLoginDto loginDto, HttpServletResponse response) {
        UserEntity userToLogin = UserLoginDtoToUserEntityMapper.INSTANCE.map(loginDto);
        return authenticationService.authenticate(userToLogin, response);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse logout(HttpServletResponse response, @AuthenticationPrincipal UserEntity userEntity) {
        return authenticationService.logout(userEntity, response);
    }

    @GetMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    public String verify() {
        return "Authenticated :)";
    }
}
