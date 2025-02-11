package com.ifm.devsam.springsec.controllers;

import com.ifm.devsam.springsec.domain.dto.UserDto;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.mappers.UserEntityToUserDtoMapper;
import com.ifm.devsam.springsec.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    public UserDto checkForLoginNewApproach(@AuthenticationPrincipal UserEntity userEntity) {
        return UserEntityToUserDtoMapper.INSTANCE.map(userEntity);
    }


//    @GetMapping("/{email}")
//    @ResponseStatus(HttpStatus.OK)
//    public UserDto getUserByEmail(@PathVariable String email) {
//        return UserEntityToUserDtoMapper.INSTANCE.map(userService.getUserByEmail(email).get());
//    }
//
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserEntityToUserDtoMapper.INSTANCE::map)
                .collect(Collectors.toList());
    }
}