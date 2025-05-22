package com.example.planner.database.user;

import com.example.planner.common.base.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class User extends Entity {
    @Setter
    private Long userId;
    private String userName;
    private String email;
    private String password;

    // copy constructor
    public User(Long userId, String userName, String email, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        super.createdAt = createdAt;
        super.updatedAt = updatedAt;
    }

    // create constructor
    public User(String userName, String email, String password) {
        super();
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public void update(String userName, String password) {
        super.update();
        this.userName = userName;
        this.password = password;
    }
}
