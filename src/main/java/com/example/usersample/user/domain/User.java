package com.example.usersample.user.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;
    private String email;
    private String password;
    private String address;
    private String nickname;

    @Builder
    public User(Long id, String email, String password, String address, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.address = address;
        this.nickname = nickname;
    }

    // 유저 업데이트
    public User update(UserUpdate userUpdate) {
        this.address = userUpdate.getAddress();
        this.nickname = userUpdate.getNickname();
        return this;
    }

    // 유저 생성시 사용되는 정적 팩터리 메서드
    public static User create(UserCreate userCreate) {
        return User.builder()
                .email(userCreate.getEmail())
                .password(userCreate.getPassword())
                .address(userCreate.getAddress())
                .nickname(userCreate.getNickname())
                .build();
    }
}
