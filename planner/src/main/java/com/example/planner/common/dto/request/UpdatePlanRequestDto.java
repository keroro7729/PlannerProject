package com.example.planner.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlanRequestDto {
    private String planText;
    private Boolean anonymity;
    private String password;
}
