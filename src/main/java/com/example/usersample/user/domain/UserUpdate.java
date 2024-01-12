package com.example.usersample.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserUpdate {

    private final String address;
    private final String nickname;

    public UserUpdate(@JsonProperty("address") String address,
                      @JsonProperty("nickname") String nickname) {
        this.address = address;
        this.nickname = nickname;
    }
}
