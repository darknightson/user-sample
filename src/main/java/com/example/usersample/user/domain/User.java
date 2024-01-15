package com.example.usersample.user.domain;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@ToString
public class User {

    private Long id;
    private String email;
    private String password;
    private String address;
    private String nickname;
    private String roles;

    @Builder
    public User(Long id, String email, String password, String address, String nickname, String roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.nickname = nickname;
        this.roles = roles;
    }

    public List<String> getRoleList() {
        if (this.roles != null && !this.roles.isEmpty()) {
            return List.of(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    // 유저 업데이트
    public User update(UserUpdate userUpdate) {
        this.address = userUpdate.getAddress();
        this.nickname = userUpdate.getNickname();
        return this;
    }

    // 유저 생성시 사용되는 정적 팩터리 메서드
    public static User create(UserCreate userCreate, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(userCreate.getEmail())
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .address(userCreate.getAddress())
                .nickname(userCreate.getNickname())
                .roles(userCreate.getRoles())
                .build();
    }
}
