package com.example.planner.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    private String userName;
    private String email;
    private String password;
}
