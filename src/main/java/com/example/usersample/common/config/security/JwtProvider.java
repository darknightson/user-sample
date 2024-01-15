package com.example.usersample.common.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.usersample.user.domain.User;
import com.example.usersample.user.service.port.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

// 시큐리티가 필터 중에 BasicAuthenticationFilter 라는 것이 있다.
// 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있다.
// 만약에 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지 않는다.
@Slf4j
public class JwtProvider extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    public JwtProvider(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("인증이나 권한이 필요한 주소가 요청됨");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader = " + jwtHeader);

        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰을 검증해서 정상적인 사용자인지 검증한다.
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        String email = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken)
                .getClaim("username").asString();

        if (email != null ) {
            // 서명이 정상적으로 됨
            System.out.println("username = " + email);
            System.out.println("username = " + email);
            User userEntity = userRepository.findByUser(email).orElseThrow(
                    () -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null,
                    principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장한다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }
}
