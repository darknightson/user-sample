package com.example.usersample.user.service.port;

import com.example.usersample.user.domain.User;
import com.example.usersample.user.infrastructure.UserEntity;
import com.example.usersample.user.infrastructure.UserJpaRepository;
import com.example.usersample.user.infrastructure.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Test
    void 유저_ID_로_조회할_수_있다() {

        // given
        // 가정: id가 1인 사용자가 존재
        long userId = 1L;

        UserEntity userEntity = UserEntity.builder()
                .id(userId)
                .email("test@kakao.com")
                .address("서울시 강남구")
                .nickname("kakao")
                .password("1234")
                .build();

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        // 테스트 실행
        User result = userRepository.findById(userId).get();
        // 검증
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }
}