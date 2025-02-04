package com.ifm.devsam.springsec.controllers;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.dto.UserDto;
import com.ifm.devsam.springsec.domain.dto.UserLoginDto;
import com.ifm.devsam.springsec.domain.dto.UserRegistrationDto;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.mappers.RegisterDtoToUserEntityMapper;
import com.ifm.devsam.springsec.mappers.UserEntityToUserDtoMapper;
import com.ifm.devsam.springsec.services.AuthenticationService;
import com.ifm.devsam.springsec.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse register(@RequestBody UserRegistrationDto registerDto) {
        UserEntity userToRegister = RegisterDtoToUserEntityMapper.INSTANCE.map(registerDto);
//        userService.createUser(userToRegister);
        return authenticationService.register(userToRegister);
    }

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse login(@RequestBody UserLoginDto loginDto) {

//        return authenticationService.authenticate()
        return null;
    }

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserByEmail(@PathVariable String email) {
        return UserEntityToUserDtoMapper.INSTANCE.map(userService.getUserByEmail(email).get());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserEntityToUserDtoMapper.INSTANCE::map)
                .collect(Collectors.toList());
    }
}