package com.ifm.devsam.springsec.services.impl;

import com.ifm.devsam.springsec.domain.entity.DepotEntity;
import com.ifm.devsam.springsec.repositories.DepotRepository;
import com.ifm.devsam.springsec.services.DepotService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepotServiceImpl implements DepotService {

    private final DepotRepository depotRepository;
    private final static BigDecimal DEFAULT_DEPOT_AMOUNT = new BigDecimal("0.00");

    public DepotServiceImpl(DepotRepository depotRepository) {
        this.depotRepository = depotRepository;
    }

    @Override
    public DepotEntity getEmptyDepot() {
        return new DepotEntity(null, DEFAULT_DEPOT_AMOUNT, null);
    }
}
