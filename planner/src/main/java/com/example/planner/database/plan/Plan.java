package com.example.planner.database.plan;

import com.example.planner.common.base.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class Plan extends Entity {

    // 생성시 자동 할당된 키값을 할당하기 위해 setter 추가
    @Setter
    private Long planId;

    private String planText;

    private Boolean anonymity;

    private Long userId; //fk

    // copy constructor
    public Plan(Long planId, String planText, Boolean anonymity, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.planId = planId;
        this.planText = planText;
        this.anonymity = anonymity;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
        this.userId = userId;
    }

    // create constructor
    public Plan(String planText, Boolean anonymity, Long userId) {
        super();
        this.planText = planText;
        this.anonymity = anonymity;
        this.userId = userId;
    }

    public void update(String planText, Boolean anonymity) {
        this.planText = planText;
        this.anonymity = anonymity;
        super.update();
    }
}
