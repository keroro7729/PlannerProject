package com.example.planner.common.dto.response;

import com.example.planner.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private String userName;
    private String email;

    public UserResponseDto(User user) {
        userName = user.getUserName();
        email = user.getEmail();
    }
}
