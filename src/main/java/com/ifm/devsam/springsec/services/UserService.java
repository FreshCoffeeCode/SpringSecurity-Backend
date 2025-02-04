package com.ifm.devsam.springsec.services;

import com.ifm.devsam.springsec.domain.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void createUser(UserEntity user);

    UserEntity getUserById(Long id);

    Optional<UserEntity> getUserByEmail(String email);

    List<UserEntity> getAllUsers();
}