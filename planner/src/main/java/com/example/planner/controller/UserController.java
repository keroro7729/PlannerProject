package com.example.planner.controller;

import com.example.planner.common.dto.request.CreateUserRequestDto;
import com.example.planner.common.dto.request.DeleteUserRequestDto;
import com.example.planner.common.dto.request.LoginRequestDto;
import com.example.planner.common.dto.request.UpdateUserRequestDto;
import com.example.planner.common.dto.response.RegisterResponseDto;
import com.example.planner.common.dto.response.UserResponseDto;
import com.example.planner.database.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody CreateUserRequestDto body) {
        return ResponseEntity.ok(service.register(body));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto body,
                                                 HttpServletRequest request) {
        service.login(body, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<UserResponseDto> getCurrentUser(HttpServletRequest request) {
        return ResponseEntity.ok(service.getCurrentUser(request));
    }

    @PatchMapping
    public ResponseEntity<UserResponseDto> updateCurrentUser(@RequestBody UpdateUserRequestDto body,
                                                             HttpServletRequest request) {
        return ResponseEntity.ok(service.updateCurrentUser(body, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCurrentUser(@RequestBody DeleteUserRequestDto body,
                                                  HttpServletRequest request) {
        service.deleteCurrentUser(body, request);
        return ResponseEntity.noContent().build();
    }
}
