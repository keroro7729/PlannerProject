package com.example.planner.common.dto.request;

import lombok.Getter;

@Getter
public class CreatePlanRequestDto {
    private String planText;
    private String userName;
    private String password;
}
