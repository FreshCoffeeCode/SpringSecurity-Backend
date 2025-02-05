package com.ifm.devsam.springsec.services;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.entity.UserEntity;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    AuthenticationResponse register(UserEntity user, HttpServletResponse response);

    AuthenticationResponse authenticate(UserEntity user);
}
