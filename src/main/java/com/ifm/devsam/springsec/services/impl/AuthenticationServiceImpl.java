package com.ifm.devsam.springsec.services.impl;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.exceptions.custom.UserNotFoundException;
import com.ifm.devsam.springsec.security.services.JwtService;
import com.ifm.devsam.springsec.services.AuthenticationService;
import com.ifm.devsam.springsec.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    private final static int ONE_DAY = 86400 * 1000;


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

    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);

        cookie.setHttpOnly(true);       // Prevent access to cookie from JavaScript
        cookie.setSecure(true);         // Only send cookie over HTTPS
        cookie.setPath("/");            // Cookie should be available across the entire domain
        cookie.setMaxAge((int) ONE_DAY / 1000);  // Set expiry time for the cookie (same as JWT expiration)
        cookie.setAttribute("SameSite", "Lax");
        response.addCookie(cookie);
    }

}
