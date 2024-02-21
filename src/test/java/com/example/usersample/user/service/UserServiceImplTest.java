package com.example.usersample.user.service;

import com.example.usersample.user.domain.User;
import com.example.usersample.user.service.port.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void 유저의_아이디로_유저_정보를_가져온다_spy_어노테이션_사용() {
        // given
        User thenReturnUser = User.builder()
                .id(1L)
                .email("test@kakao.com")
                .nickname("test")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(thenReturnUser));

        // 스파이 객체의 특정 메서드 동작을 변경
        String fakeEncodedPassword = "fakeEncodedPassword";
        doReturn(fakeEncodedPassword).when(bCryptPasswordEncoder).encode(anyString());

        // when
        User result = userService.getById(1L);

        // then
        verify(userRepository).findById(1L);
        Assertions.assertThat(result.getNickname()).isEqualTo(thenReturnUser.getNickname());

        // 추가적인 검증
        String testPassword = "password";
        String encodedPassword = bCryptPasswordEncoder.encode(testPassword);
        Assertions.assertThat(encodedPassword).isEqualTo(fakeEncodedPassword);
    }
    @Test
    void 유저의_아이디로_유저_정보를_가져온다_InjectionMock_어노테이션_사용() {

        // given (준비)
        User thenReturnUser = User.builder()
                .id(1l)
                .email("test@kakao.com")
                .nickname("test")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(thenReturnUser));
        // when (실행)
        User result = userService.getById(1l);
        // then (단언 assert)
        verify(userRepository).findById(1L);
        Assertions.assertThat(result.getNickname()).isEqualTo(thenReturnUser.getNickname());
    }

    @Test
    void 유저의_아이디로_유저_정보를_가져온다_Mock_어노테이션_사용() {

        // given (준비)
        User thenReturnUser = User.builder()
                .id(1l)
                .email("test@kakao.com")
                .nickname("test")
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(thenReturnUser));
        UserServiceImpl userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());
        // when (실행)
        User result = userService.getById(1l);

        // then (단언 assert)
        verify(userRepository).findById(1L);
        Assertions.assertThat(result.getNickname()).isEqualTo(thenReturnUser.getNickname());
    }

    @Test
    void 유저의_아이디로_유저_정보를_가져온다() {

        // given (준비)
        UserRepository userRepository = mock(UserRepository.class);
        User thenReturnUser = User.builder()
                .id(1l)
                .email("anthony.son@kakaoent.com")
                .nickname("anthony")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(thenReturnUser));

        // when (실행)
        UserServiceImpl userService = new UserServiceImpl(userRepository, new BCryptPasswordEncoder());
        User result = userService.getById(1l);

        // then (단언 assert)
        verify(userRepository).findById(1L);
        Assertions.assertThat(result.getNickname()).isEqualTo(thenReturnUser.getNickname());
    }
}