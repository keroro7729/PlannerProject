package com.example.planner.common.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResourceNotFoundException extends RuntimeException {
    private final String entityName;
    private final Long id;

    public ResourceNotFoundException(String entity, Long id) {
        super(entity+" not found: "+id);
        this.entityName = entity;
        this.id = id;
    }
}
