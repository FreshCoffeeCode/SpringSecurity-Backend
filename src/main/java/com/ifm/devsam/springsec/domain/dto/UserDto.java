package com.ifm.devsam.springsec.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Email
    private String email;
    private String firstName;
    private String lastName;
    private DepotDto depotDto;
}