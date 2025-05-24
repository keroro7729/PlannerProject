package com.example.planner.utils;

import com.example.planner.common.exceptions.SessionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static void setUserId(HttpServletRequest request, Long userId) {
        HttpSession session = request.getSession(false);
        if(session == null)
            throw new SessionException(request);

        session.setAttribute("userId", userId);
    }

    public static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null)
            throw new SessionException(request);

        Long userId = (Long) session.getAttribute("userId");
        if(userId == null)
            throw new SessionException("userId", session);

        return userId;
    }
}
