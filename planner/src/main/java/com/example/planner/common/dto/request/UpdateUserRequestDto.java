package com.example.planner.common.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
    private String userName;
    private String oldPassword;
    private String newPassword;
}
