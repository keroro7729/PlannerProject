package com.example.planner.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    public static void setUserId(HttpServletRequest request, Long userId) {
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
    }

    public static Long getUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            throw new RuntimeException("session not found. request info: "+request.getRemoteAddr());
        }
        Long userId = (Long) session.getAttribute("userId");
        if(userId == null) {
            throw new RuntimeException("session has no userId: "+session.getId());
        }
        return userId;
    }
}
