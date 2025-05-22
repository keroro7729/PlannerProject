package com.example.planner.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterResponseDto {
    private Boolean success;
    private String message;
    private String loginApiUri;
}
