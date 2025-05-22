package com.example.planner.common.dto.response;

import com.example.planner.database.plan.Plan;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PlanResponseDto {
    private Long planId;
    private String planText;
    private Boolean anonymity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PlanResponseDto(Plan plan) {
        planId = plan.getPlanId();
        planText = plan.getPlanText();
        anonymity = plan.getAnonymity();
        createdAt = plan.getCreatedAt();
        updatedAt = plan.getUpdatedAt();
    }
}
