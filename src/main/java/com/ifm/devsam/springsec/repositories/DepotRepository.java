package com.ifm.devsam.springsec.repositories;

import com.ifm.devsam.springsec.domain.entity.DepotEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRepository extends CrudRepository<DepotEntity, Long> {
}
