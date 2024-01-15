package com.example.usersample.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> fakeLogin(@RequestParam("email") String email,
                                       @RequestParam("password") String password) {
        // Swagger 문서화를 위한 가짜 엔드포인트
        // 실제로 이 메서드가 호출되는 것을 방지
        throw new IllegalStateException("이 메서드는 Swagger 문서화를 위한 것이며, 실제로 호출되면 안 됩니다.");
    }
}
