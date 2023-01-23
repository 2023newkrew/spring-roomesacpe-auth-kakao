package nextstep.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.member.Member;
import nextstep.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;
    @MockBean
    private MemberService memberService; // WebMvcConfigurer 생성을 위한 Mock
    @MockBean
    private JwtTokenProvider jwtTokenProvider; // WebMvcConfigurer 생성을 위한 Mock
    @Autowired
    private ObjectMapper objectMapper;
    private final String DUMMY_TOKEN_STRING = "abcde";

    @DisplayName("토큰 생성 로직 호출")
    @Test
    public void create() throws Exception {
        //given
        TokenRequest requestBody = new TokenRequest("username", "password");
        Member member = Member.builder().username("username").password("password").name("name").phone("010-1234-5678").build();
        String content = objectMapper.writeValueAsString(requestBody);
        when(memberService.findByUsername(ArgumentMatchers.any(String.class))).thenReturn(member);
        when(authService.createToken(ArgumentMatchers.any(Member.class), ArgumentMatchers.any(String.class))).thenReturn(new TokenResponse(DUMMY_TOKEN_STRING));

        //when
        String responseBody = mockMvc.perform(post("/login/token")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk()).andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        TokenResponse tokenResponse = objectMapper.readValue(responseBody, TokenResponse.class);
        assertThat(tokenResponse.getAccessToken()).isEqualTo(DUMMY_TOKEN_STRING);
    }
}
