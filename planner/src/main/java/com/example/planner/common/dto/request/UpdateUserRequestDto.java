package com.example.planner.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
    private String userName;
    @NotBlank(message = "oldPassword 필수")
    private String oldPassword;
    private String newPassword;
}
