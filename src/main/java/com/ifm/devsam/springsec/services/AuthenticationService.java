package com.ifm.devsam.springsec.services;

import com.ifm.devsam.springsec.domain.dto.AuthenticationResponse;
import com.ifm.devsam.springsec.domain.entity.UserEntity;

public interface AuthenticationService {

    AuthenticationResponse register(UserEntity user);

    AuthenticationResponse authenticate(UserEntity user);
}
