package com.example.usersample.user.controller;

import com.example.usersample.common.domain.ApiResponse;
import com.example.usersample.user.controller.port.UserService;
import com.example.usersample.user.controller.response.UserResponse;
import com.example.usersample.user.domain.UserCreate;
import com.example.usersample.user.domain.UserUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저(users)", description = "유저 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "email 을 통한 유저 조회", description = "email 을 통해 유저를 조회합니다.")
    @Parameter(name = "email", description = "유저 이메일", required = true)
    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable String email) {
        UserResponse userResponse = UserResponse.from(userService.getUser(email));
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, HttpStatus.OK.series().name(), userResponse));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(UserResponse.from(userService.getUsers()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody UserCreate userCreate, BindingResult bindingResult) {

        if ( bindingResult.hasErrors() ) {
            return getErrors(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(HttpStatus.CREATED,
                        HttpStatus.CREATED.series().name(),
                        UserResponse.from(userService.save(userCreate))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable Long id, @RequestBody UserUpdate userUpdate,
                                               BindingResult bindingResult) {
        if ( bindingResult.hasErrors() ) {
            return getErrors(bindingResult);
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.of(HttpStatus.OK,
                        HttpStatus.OK.series().name(),
                        UserResponse.from(userService.update(id, userUpdate))));
    }

    private static ResponseEntity<ApiResponse> getErrors(BindingResult bindingResult) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.of(HttpStatus.BAD_REQUEST,
                        HttpStatus.BAD_REQUEST.series().name(),
                        bindingResult.getAllErrors()));
    }
}
