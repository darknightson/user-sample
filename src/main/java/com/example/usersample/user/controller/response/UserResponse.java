package com.example.usersample.user.controller.response;

import com.example.usersample.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String email;
    private String password;
    private String address;
    private String nickname;

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .address(user.getAddress())
            .nickname(user.getNickname())
            .build();
    }

    public static List<UserResponse> from(List<User> users) {
        return users.stream()
                .map(UserResponse::from)
                .collect(toList());
    }
}
