package com.example.usersample.user.controller;

import com.example.usersample.user.controller.port.UserService;
import com.example.usersample.user.controller.response.UserResponse;
import com.example.usersample.user.domain.UserCreate;
import com.example.usersample.user.domain.UserUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
        return ResponseEntity.ok(UserResponse.from(userService.getUser(email)));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(UserResponse.from(userService.getUsers()));
    }

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody UserCreate userCreate) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.from(userService.save(userCreate)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserUpdate userUpdate) {
        return ResponseEntity.ok(UserResponse.from(userService.update(id, userUpdate)));
    }
}
