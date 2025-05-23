package com.example.planner.common.dto.request;

import lombok.Getter;

@Getter
public class CreatePlanRequestDto {
    private String planText;
    private Boolean anonymity;
    private String password;
}
