package com.example.planner.plan;

import com.example.planner.common.base.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class Plan extends Entity {

    // 생성시 자동 할당된 키값을 할당하기 위해 setter 추가
    @Setter
    private Long planId;

    private String planText;
    private String userName;

    private String password;

    // copy constructor
    public Plan(Long planId, String planText, String userName, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.planId = planId;
        this.planText = planText;
        this.userName = userName;
        this.password = password;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }

    // create constructor
    public Plan(String planText, String userName, String password) {
        super();
        this.planText = planText;
        this.userName = userName;
        this.password = password;
    }

    public void update(String planText) {
        this.planText = planText;
        super.update();
    }

    public void update(String planText, String userName) {
        this.planText = planText;
        this.userName = userName;
        super.update();
    }
}
