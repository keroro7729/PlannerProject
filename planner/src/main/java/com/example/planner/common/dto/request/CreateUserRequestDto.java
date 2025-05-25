package com.example.planner.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequestDto {
    @NotBlank(message = "userName 필수")
    private String userName;
    @NotBlank(message = "email 필수")
    private String email;
    @NotBlank(message = "password 필수")
    private String password;
}
