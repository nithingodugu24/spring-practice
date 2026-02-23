package com.nithingodugu.hospitalManagement.dto;

import com.nithingodugu.hospitalManagement.entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class SignupRequestDto {

    private String email;
    private String password;
    private String name;
    private Set<RoleType> roles;

}
