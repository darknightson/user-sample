package com.example.usersample.user.controller.port;

import com.example.usersample.user.domain.User;
import com.example.usersample.user.domain.UserCreate;
import com.example.usersample.user.domain.UserUpdate;
import com.example.usersample.user.service.UserServiceImpl;
import com.example.usersample.user.service.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    User init() {
        return User.builder()
                .id(1l)
                .email("test@kakao.com")
                .address("서울시 강남구")
                .nickname("kakao")
                .password("1234")
                .build();
    }

    @Test
    void 유저를_수정_할_수_있다() {
        // given (준비)
        long id = 1L;

        UserUpdate userUpdate = UserUpdate.builder()
                .address("경기도 판교")
                .nickname("홍길동")
                .build();

        User existingUser = init();
        User updatedUser = init();
        updatedUser.setAddress(userUpdate.getAddress());
        updatedUser.setNickname(userUpdate.getNickname());

        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // when (실행)
        User result = userService.update(id, userUpdate);

        // then (단언 assert)
        assertNotNull(result);
        assertThat(result.getNickname()).isEqualTo(userUpdate.getNickname());
        assertThat(result.getAddress()).isEqualTo(userUpdate.getAddress());

        // userRepository.findById와 userRepository.save가 호출되었는지 검증
        verify(userRepository).findById(id);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void 유저_이메일로_유저를_조회할_수_있다() {

        // given (준비)
         String email = "test@kakao.com";

        // when (실행)
        when(userRepository.findByUser(email)).thenReturn(Optional.of(init()));
        User result = userService.getUser(email);
        // then (단언 assert)
        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getNickname()).isEqualTo("kakao");

    }

    @Test
    void 다수의_유저를_조회할_수_있다() {

        // given (준비)
        List<User> userList = List.of(init(), init(), init());

        // when (실행)
        when(userRepository.findUsers()).thenReturn(Optional.of(userList));
        List<User> users = userService.getUsers();

        // then (단언 assert)
        assertNotNull(users);
        assertThat(users.size()).isEqualTo(3);
    }

    @Test
    void 유저를_등록할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("test@kakao.com")
                .password("1234")
                .address("서울시 강남구")
                .nickname("kakao")
                .roles("ROLE_USER")
                .build();

        User expectedUser = init();
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // when
        User result = userService.save(userCreate);

        // then
        assertNotNull(result);
        assertThat(result.getEmail()).isEqualTo(userCreate.getEmail());
        assertThat(result.getAddress()).isEqualTo(userCreate.getAddress());
        assertThat(result.getNickname()).isEqualTo(userCreate.getNickname());

        // userRepository.save가 호출되었는지 검증
        verify(userRepository).save(any(User.class));
    }

}