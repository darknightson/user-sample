package com.example.usersample.user.domain;

import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * 유저 도메인 소형 테스트
 */

class UserTest {

    @Test
    void 유저를_생성할_수_있습니다() {

        // given (준비)
         UserCreate userCreate = UserCreate.builder()
                 .email("test@kakao.com")
                 .nickname("test")
                 .address("서울시 강남구")
                 .password("1234")
                 .roles("ROLE_USER")
                 .build();

        // when (실행)
        User user = User.create(userCreate, new BCryptPasswordEncoder());

        // then (단언 assert)
        assertThat(user.getEmail()).isEqualTo(userCreate.getEmail());
        assertThat(user.getNickname()).isEqualTo(userCreate.getNickname());
        assertThat(user.getAddress()).isEqualTo(userCreate.getAddress());
    }

    @Test
    void 유저를_수정할_수_있다() {

        // given (준비)

        User user = User.builder()
                .id(1L)
                .email("test@kakao.com")
                .nickname("kakao")
                .address("서울시 강남구")
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("test")
                .address("서울시 강남구")
                .build();

        // when (실행)
        User updateUser = user.update(userUpdate);

        // then (단언 assert)
        assertThat(updateUser.getNickname()).isEqualTo(userUpdate.getNickname());
        assertThat(updateUser.getAddress()).isEqualTo(userUpdate.getAddress());
    }
}