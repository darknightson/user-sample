package com.example.usersample;


import com.example.usersample.user.domain.User;
import com.example.usersample.user.service.port.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        userRepository.save(User.builder()
                .email("test@kakao.com")
                .password(passwordEncoder.encode("1111"))
                .roles("ROLE_USER")
                .build());
    }
}
