package nextstep.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.presentation.LoginController;
import nextstep.service.LoginService;
import nextstep.utils.JwtTokenProvider;
import nextstep.dto.request.TokenRequest;
import nextstep.dto.response.TokenResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private LoginService loginService;

    @Test
    void 사용자_아이디와_비밀번호가_일치할_경우_토큰이_발급된다() throws Exception {
        // given
        String token = "access-token";
        TokenRequest tokenRequest = new TokenRequest("username", "password");

        given(loginService.login(any(TokenRequest.class)))
                .willReturn(new TokenResponse(token));

        // when
        ResultActions perform = mockMvc.perform(post("/login/token")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(tokenRequest)));

        // then
        MockHttpServletResponse response = perform.andReturn().getResponse();

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", equalTo(token)));

    }

}
