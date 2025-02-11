package com.ifm.devsam.springsec.services.impl;

import com.ifm.devsam.springsec.domain.entity.UserEntity;
import com.ifm.devsam.springsec.exceptions.custom.UserAlreadyExistsException;
import com.ifm.devsam.springsec.repositories.UserRepository;
import com.ifm.devsam.springsec.services.DepotService;
import com.ifm.devsam.springsec.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final DepotService depotService;
    private final UserRepository userRepository;
//    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserServiceImpl(DepotService depotService, UserRepository userRepository) {
        this.depotService = depotService;
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserEntity user) {
        Optional<UserEntity> checkUserToSave = getUserByEmail(user.getEmail());
        if (checkUserToSave.isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
//        user.setPassword(encoder.encode(user.getPassword()));
        user.setDepot(depotService.getEmptyDepot());
        userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.getUserEntityByUserID(id);
    }

    @Override
    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepository.findUserEntityByEmail(email);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return (List<UserEntity>) userRepository.findAll();
    }
}
