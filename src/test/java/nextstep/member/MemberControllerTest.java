package nextstep.member;

import nextstep.auth.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@ExtendWith(MockitoExtension.class)
@Import(JwtTokenProvider.class)
class MemberControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    MemberService memberService;

    @Autowired
    MockMvc mockMvc;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("토큰을 사용해 내 정보 열람 테스트")
    class GetMyInfoByToken {

        private Long userId = 1L;
        private String username = "username";
        private String password = "password";
        private String name = "name";
        private String phone = "010-1234-5678";
        private String userToken = "Bearer " + jwtTokenProvider.createToken(String.valueOf(userId), new ArrayList<>());

        @Test
        @DisplayName("유효한 토큰일 경우 200과 자신의 정보를 응답해야 한다.")
        void should_successfully_when_validToken() throws Exception {
            when(memberService.findById(userId)).thenReturn(new Member(userId, username, password, name, phone));
            mockMvc.perform(get("/members/me")
                            .header(AUTHORIZATION, userToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(userId))
                    .andExpect(jsonPath("$.username").value(username))
                    .andExpect(jsonPath("$.password").value(password))
                    .andExpect(jsonPath("$.name").value(name))
                    .andExpect(jsonPath("$.phone").value(phone));
        }

        @Test
        @DisplayName("잘못된 토큰일 경우 401 UnAuthorization을 응답해야 한다.")
        void should_401UnAuthorization_when_invalidToken() throws Exception {
            when(memberService.findById(userId)).thenReturn(new Member(userId, username, password, name, phone));
            mockMvc.perform(get("/members/me")
                            .header(AUTHORIZATION, ""))
                    .andExpect(status().isUnauthorized());
        }
    }
}