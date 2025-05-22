package com.example.planner.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlanRequestDto {
    private String planText;
    private String userName;
    private String password;
}
