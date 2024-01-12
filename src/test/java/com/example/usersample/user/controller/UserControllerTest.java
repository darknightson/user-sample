package com.example.usersample.user.controller;

import com.example.usersample.user.domain.User;
import com.example.usersample.user.domain.UserCreate;
import com.example.usersample.user.domain.UserUpdate;
import com.example.usersample.user.service.port.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("local")
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @WithMockUser(username = "test", roles = {"USER"})
    @Test
    void 유저의_주소와_닉네임을_수정할_수_있다() throws Exception {

        // given (준비)
        UserUpdate userUpdate = UserUpdate.builder()
                .address("Seoul")
                .nickname("updateNickName")
                .build();

        // when (실행)
        mockMvc.perform(put("/api/v1/users/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userUpdate))
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(authenticated().withUsername("test"));

        User result = userRepository.findById(1l).get();

        // then (단언 assert)
        assertThat(result.getAddress()).isEqualTo(userUpdate.getAddress());

    }


    @WithMockUser(username = "test", roles = {"USER"})
    @Test
    void 신규_유저를_등록할_수_있다() throws Exception {

        // given (준비)
        UserCreate userCreate = UserCreate.builder()
                .email("test4@kakao.com")
                .password("test4")
                .nickname("testnick4")
                .address("Seoul")
                .build();

        // when (실행)
        mockMvc.perform(post("/api/v1/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userCreate))
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(authenticated().withUsername("test"));

        User result = userRepository.findByUser(userCreate.getEmail()).get();

        // then (단언 assert)
        assertThat(result.getEmail()).isEqualTo(userCreate.getEmail());
    }

    @WithMockUser(username = "test", roles = {"USER"})
    @Test
    void 유저_이메일로_조회를_한다() throws Exception {

        // given (준비)
        // when (실행)
        // then (단언 assert)
        mockMvc.perform(get("/api/v1/users/test1@kakao.com"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.email").value("test1@kakao.com"));
    }

    @WithMockUser(username = "test", roles = {"USER"})
    @Test
    void 전체_유저를_조회할_수_있다() throws Exception {

        // given (준비)
        // when (실행)
        // then (단언 assert)
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3))) // 배열 크기가 3인지 검증
                .andExpect(jsonPath("$[0].nickname").value("testnick1"))
                .andExpect(jsonPath("$[0].email").value("test1@kakao.com"));
    }
}