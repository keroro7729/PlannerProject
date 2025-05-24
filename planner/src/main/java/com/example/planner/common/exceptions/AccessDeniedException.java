package com.example.planner.common.exceptions;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccessDeniedException extends RuntimeException {
    private final String clientMessage;
    private final String entity;
    private final Long id, userId;

    public AccessDeniedException(String entity, Long id, Long userId) {
        super("access denied: "+entity+"-id="+id+" user-id="+userId);
        this.entity = entity;
        this.id = id;
        this.userId = userId;
        clientMessage = "cant access to "+entity+": password not match";
    }
}
