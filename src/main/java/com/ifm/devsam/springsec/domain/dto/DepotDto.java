package com.ifm.devsam.springsec.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class DepotDto {

    private BigDecimal currentBalance;
}