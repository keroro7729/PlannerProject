package com.example.planner.database.user;

import com.example.planner.common.dto.request.CreateUserRequestDto;
import com.example.planner.common.dto.request.DeleteUserRequestDto;
import com.example.planner.common.dto.request.LoginRequestDto;
import com.example.planner.common.dto.request.UpdateUserRequestDto;
import com.example.planner.common.dto.response.RegisterResponseDto;
import com.example.planner.common.dto.response.UserResponseDto;
import com.example.planner.common.exceptions.LoginFailedException;
import com.example.planner.common.exceptions.ResourceNotFoundException;
import com.example.planner.database.deletelog.DeleteLog;
import com.example.planner.database.deletelog.DeleteLogRepository;
import com.example.planner.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final DeleteLogRepository deleteLogRepository;

    public RegisterResponseDto register(CreateUserRequestDto body) {
        if(repository.findByEmail(body.getEmail()).isPresent()) {
            return new RegisterResponseDto(false, "email already exist", null);
        }
        //else{ 이메일 검증 로직 }
        User user = new User(body.getUserName(), body.getEmail(), body.getPassword());
        user = repository.save(user);
        //login(user.getUserId(), request); 로그인 과정으로 리다이렉트 하도록 유도
        return new RegisterResponseDto(true, "register success!", "/api/users/login");
    }

    public void login(LoginRequestDto body, HttpServletRequest request) {
        User user = repository.findByEmail(body.getEmail())
                .orElseThrow(() -> new LoginFailedException(body.getEmail()));
        checkPassword(user.getPassword(), body.getPassword());
        login(user.getUserId(), request);
    }

    private void login(Long userId, HttpServletRequest request) {
        request.getSession();
        SessionUtil.setUserId(request, userId);
    }

    public void checkPassword(Long userId, String password) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));
        checkPassword(user.getPassword(), password);
    }
    private void checkPassword(String password, String input) {
        if(!password.equals(input)) {
            throw new LoginFailedException();
        }
    }

    public UserResponseDto getCurrentUser(HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));
        return new UserResponseDto(user);
    }

    public UserResponseDto updateCurrentUser(UpdateUserRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));
        checkPassword(user.getPassword(), body.getOldPassword());

        String userName = body.getUserName() == null ? user.getUserName() : body.getUserName();
        String password = body.getNewPassword() == null ? user.getPassword() : body.getNewPassword();
        user.update(userName, password);
        repository.update(user);
        return new UserResponseDto(user);
    }

    public void deleteCurrentUser(DeleteUserRequestDto body, HttpServletRequest request) {
        Long userId = SessionUtil.getUserId(request);
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));
        checkPassword(user.getPassword(), body.getPassword());
        repository.delete(userId);
        deleteLogRepository.save(new DeleteLog("user", userId));
    }

    public String getUserName(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));
        return user.getUserName();
    }
}
