package com.example.planner.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionException extends RuntimeException {
    private final HttpServletRequest request;
    private final HttpSession session;
    private final String clientMessage = "You're not logged in. Please log in first";

    // 세션을 찾지 못한 경우
    public SessionException(HttpServletRequest request) {
        super("cant find session: request-addr="+request.getRemoteAddr());
        this.request = request;
        this.session = null;
    }

    // 세션에 찾는 속성이 없는 경우
    public SessionException(String attrName, HttpSession session) {
        super("cant find "+attrName+" from session: "+session.getId());
        this.request = null;
        this.session = session;
    }
}
