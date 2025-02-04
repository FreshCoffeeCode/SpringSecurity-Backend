package com.ifm.devsam.springsec.services.impl;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.exceptions.custom.UserNotFoundException;
import com.ifm.devsam.springsec.security.services.JwtService;
import com.ifm.devsam.springsec.services.AuthenticationService;
import com.ifm.devsam.springsec.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

//    private final static Pattern NAME_PATTERN = Pattern.compile("^([a-zA-Z]+)\\.([a-zA-Z]+)@.*$", Pattern.CASE_INSENSITIVE);

    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse register(UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(UserEntity user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        var userToFind = userService.getUserByEmail(user.getEmail());
        if (userToFind.isEmpty()) throw new UserNotFoundException("User not found");
        var jwtToken = jwtService.generateToken(userToFind.get());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
