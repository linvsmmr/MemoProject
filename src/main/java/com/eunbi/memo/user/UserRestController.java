package com.eunbi.memo.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserRestController {

    private final UserService userService;


    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입 API
    @PostMapping("/join-process")
    public Map<String, String> join(@RequestParam String loginId
    , @RequestParam String password
    , @RequestParam String name
    , @RequestParam String email) {

        Map<String, String> resultMap = new HashMap<>();
        if (userService.createUser(loginId, password, name, email)) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }


        return resultMap;

    }


    @GetMapping("/duplicate-id")
    public Map<String, Boolean> isDuplicateId(@RequestParam String loginId) {

        Map<String, Boolean> resultMap = new HashMap<>();

        if (userService.isDuplicateId(loginId)) {
            resultMap.put("isDuplicate", true);
        } else {
            resultMap.put("isDuplicate", false);
        }
        return resultMap;
    }


    @PostMapping("/login-process")
    public Map<String, String> login(
            @RequestParam String loginId,
            @RequestParam String password,
            HttpServletRequest request
    ) {

        Map<String, String> resultMap = new HashMap<>();

        // 사용자 정보 얻어 오기
        User user = userService.getUser(loginId, password);

        if (user != null) {
            resultMap.put("result", "success");
            // 세션에 사용자 정보를 저장
            // 요청한 대상 클라이언트에 대응되는 세션을 다루는 객체
            HttpSession session = request.getSession();

            // 세션은 해당 클라이언트의 요청에서 손쉽게 사용 가능
            // 요청마다 자주 사용되는 사용자 정보가 있다면 저장
            // user PK, 이름
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());


        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;


    }





}
