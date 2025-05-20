package com.example.planner.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public abstract class Entity {
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    public Entity() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    protected void update() {
        updatedAt = LocalDateTime.now();
    }
}
