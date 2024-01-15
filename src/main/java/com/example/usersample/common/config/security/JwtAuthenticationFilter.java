package com.example.usersample.common.config.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Date;

/*
formLogin()을 사용 하지 않으면 http://localhost:8080/login 요청이 작동을 하지 않게된다.
우린 jwt 방식을 사용하기 때문에 별도의 필터를 만들어서 해당 요청을 가로채서 작동하게 해야한다.
*/
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/login"); // 로그인 경로 설정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

            String email = request.getParameter("email");
            String password = request.getParameter("password");
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(email, password);
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            PrincipalDetails principal = (PrincipalDetails) authenticate.getPrincipal();
            log.info("로그인 완료 : {}", principal.getUser().getEmail());
            return authenticate;

    }

    // 인증 성공 시 JWT 토큰 생성 및 반환 로직 구현
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {

        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        // JWT 토큰 발급
        String jwtToken = JWT.create()
                .withSubject("cos Token")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000 * 10))) // 10분
                .withClaim("id", principal.getUser().getId()) // 비공개 클레임
                .withClaim("username", principal.getUser().getEmail()) // 비공개 클레임
                .sign(Algorithm.HMAC512("cos"));// secret key

        log.info("jwtToken : {} ", jwtToken);

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}