package com.eunbi.memo.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


@Component
public class PermissionInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws IOException {
        // 로그인이 안된 상태에서 메모와 관련된 페이지 접근을 막는다
        HttpSession session = request.getSession();

        Long userId = (Long)session.getAttribute("userId");


        String uri = request.getRequestURI();


        if (userId == null) {
            // memo로 시작하는 요청 url인 경우
            if (uri.startsWith("/memo")) {

                response.sendRedirect("/user/login");
                return false;
            }
        } else {
            if (uri.startsWith("/user")) {
                response.sendRedirect("/memo/list");
                return false;
            }
        }

        return true;
    }
}
