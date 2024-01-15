package com.example.usersample.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreate {

    @Email
    private final String email;

    private final String password;
    private final String address;
    private final String nickname;
    private final String roles;

    @Builder
    public UserCreate(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("address") String address,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("roles") String roles) {
        this.email = email;
        this.password = password;
        this.address = address;
        this.nickname = nickname;
        this.roles = roles;
    }
}
