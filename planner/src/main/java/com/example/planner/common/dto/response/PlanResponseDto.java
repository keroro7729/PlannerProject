package com.example.planner.common.dto.response;

import com.example.planner.database.plan.Plan;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PlanResponseDto {
    private Long planId;
    private String planText;
    private Boolean anonymity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // 작성자 이름이 표기되어야 함
    // userId로 user 매핑 엔티티? jdbc템플릿으로 구현 가능??
    // setter 또는 생성자에 userName 추가?
    @Setter
    private String userName;

    public PlanResponseDto(Plan plan) {
        planId = plan.getPlanId();
        planText = plan.getPlanText();
        anonymity = plan.getAnonymity();
        createdAt = plan.getCreatedAt();
        updatedAt = plan.getUpdatedAt();
    }
}
