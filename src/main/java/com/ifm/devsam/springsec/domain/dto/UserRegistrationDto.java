package com.ifm.devsam.springsec.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}