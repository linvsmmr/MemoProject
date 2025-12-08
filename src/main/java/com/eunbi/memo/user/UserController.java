package com.eunbi.memo.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/memo")
@Controller
public class UserController {


    @GetMapping("/join")
    public String join() {
        return "user/join";
    }


    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/list")
    public String list() {
        return "user/list";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        // 세션에 저장된 사용자 정보 제거
        HttpSession session = request.getSession();
//        session.removeAttribute("userId");
//        session.removeAttribute("userName");

        session.invalidate();

        return "redirect:/memo/login";


    }
    
    
}
