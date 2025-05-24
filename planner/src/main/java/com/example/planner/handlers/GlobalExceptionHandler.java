package com.example.planner.handlers;

import com.example.planner.common.exceptions.AccessDeniedException;
import com.example.planner.common.exceptions.LoginFailedException;
import com.example.planner.common.exceptions.ResourceNotFoundException;
import com.example.planner.common.dto.response.ErrorResponse;
import com.example.planner.common.exceptions.SessionException;
import com.example.planner.database.deletelog.DeleteLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final DeleteLogRepository deleteLogRepository;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        log.warn(e.getMessage());
        log.debug("{}\n{}", e, Arrays.toString(e.getStackTrace()));
        // 삭제 로그에 존재하면 삭제된 리소스라고 알려주기
        String clientMessage = deleteLogRepository.findByNameId(e.getEntityName(), e.getId()).isEmpty() ?
                        e.getMessage() :
                        e.getEntityName()+"("+e.getId()+") is deleted";
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), clientMessage);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        log.warn(e.getMessage());
        log.debug("{}\n{}", e, Arrays.toString(e.getStackTrace()));
        ErrorResponse response = new ErrorResponse(HttpStatus.FORBIDDEN.value(), e.getClientMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<ErrorResponse> handleLoginFailed(LoginFailedException e) {
        log.warn(e.getMessage());
        log.debug("{}\n{}", e, Arrays.toString(e.getStackTrace()));
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(SessionException.class)
    public ResponseEntity<ErrorResponse> handleSessionException(SessionException e) {
        log.warn(e.getMessage());
        log.debug("{}\n{}", e, Arrays.toString(e.getStackTrace()));
        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getClientMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedExceptions(Exception e) {
        log.warn(Arrays.toString(e.getStackTrace()));
        log.error(Arrays.toString(e.getStackTrace()));
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "sorry, internal server error occurred. try later"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
