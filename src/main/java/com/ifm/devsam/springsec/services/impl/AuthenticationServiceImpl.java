package com.ifm.devsam.springsec.services.impl;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.exceptions.custom.UserNotFoundException;
import com.ifm.devsam.springsec.security.services.JwtService;
import com.ifm.devsam.springsec.services.AuthenticationService;
import com.ifm.devsam.springsec.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final static int ONE_DAY = 86400 * 1000;


    @Autowired
    public AuthenticationServiceImpl(UserService userService,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse register(UserEntity user, HttpServletResponse response) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);
        var jwtToken = jwtService.generateToken(user);
        setJwtCookie(response, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(UserEntity user, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );
        Optional<UserEntity> userToFind = userService.getUserByEmail(user.getEmail());
        if (userToFind.isEmpty()) throw new UserNotFoundException("User not found");
        var jwtToken = jwtService.generateToken(userToFind.get());
        setJwtCookie(response, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse logout(HttpServletResponse response, UserEntity user) {
        String expiredToken = setRevokedJwtCookie(response, user);
        return AuthenticationResponse.builder()
                .token(expiredToken)
                .build();
    }

    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);

        cookie.setHttpOnly(true);       // Prevent access to cookie from JavaScript
        cookie.setSecure(true);         // Only send cookie over HTTPS
        cookie.setPath("/");            // Cookie should be available across the entire domain
        cookie.setMaxAge((int) ONE_DAY / 1000);  // Set expiry time for the cookie (same as JWT expiration)
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
    }

    private String setRevokedJwtCookie(HttpServletResponse response, UserEntity user) {
//        String expiredToken = jwtService.generateExpiredToken(user);
        String expiredToken = "";
        Cookie cookie = new Cookie("jwt", expiredToken);

        cookie.setHttpOnly(true);       // Prevent access to cookie from JavaScript
        cookie.setSecure(true);         // Only send cookie over HTTPS
        cookie.setPath("/");            // Cookie should be available across the entire domain
        cookie.setMaxAge(0);  // Set expiry time for the cookie (same as JWT expiration)
        cookie.setAttribute("SameSite", "None");
        response.addCookie(cookie);
        return expiredToken;
    }

}
