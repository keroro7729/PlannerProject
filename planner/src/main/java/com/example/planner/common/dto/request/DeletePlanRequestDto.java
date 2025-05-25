package com.example.planner.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePlanRequestDto {
    @NotBlank(message = "password 필수")
    private String password;
}
