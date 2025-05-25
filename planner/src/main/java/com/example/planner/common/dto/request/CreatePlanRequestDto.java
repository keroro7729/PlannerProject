package com.example.planner.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePlanRequestDto {
    @NotBlank(message = "planText 필수")
    private String planText;
    private Boolean anonymity;
    @NotBlank(message = "password 필수")
    private String password;
}
