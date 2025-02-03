package com.ifm.devsam.springsec.repositories;

import com.ifm.devsam.springsec.domain.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    List<UserEntity> getUserEntitiesByEmail(String email);

    UserEntity getUserEntityByUserID(Long userID);

    Optional<UserEntity> getUserEntityByEmail(String email);
}
