package nextstep.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import nextstep.auth.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;
import java.util.List;

import static nextstep.auth.role.Role.ROLE_ADMIN;
import static nextstep.auth.role.Role.ROLE_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
@ExtendWith(MockitoExtension.class)
@Import(JwtTokenProvider.class)
class AdminControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    AdminService adminService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    private final long adminId = 0L;
    private final long userId = 1L;
    private final long themeId = 1L;
    private final long scheduleId = 1L;
    private String adminToken;
    private String userToken;

    @BeforeEach
    void setup() {
        adminToken = jwtTokenProvider.createToken(String.valueOf(adminId), List.of(ROLE_USER, ROLE_ADMIN));
        userToken = jwtTokenProvider.createToken(String.valueOf(userId), List.of(ROLE_USER));
    }

    @Nested
    class CreateTheme {

        @Test
        void should_success_when_adminRequestCreateTheme() throws Exception {
            when(adminService.createTheme(any())).thenReturn(themeId);
            mockMvc.perform(post("/admin/themes")
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, adminToken))
                            .content(mapper.writeValueAsString(new AdminThemeRequest("테마이름", "테마설명", 22000)))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        void should_fail_when_userRequestCreateTheme() throws Exception {
            mockMvc.perform(post("/admin/themes")
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, userToken)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void should_fail_when_unauthorizedRequestCreateTheme() throws Exception {
            mockMvc.perform(post("/admin/themes")
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, "")))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class DeleteTheme {

        @Test
        void should_success_when_adminRequestDeleteTheme() throws Exception {
            doNothing().when(adminService).deleteTheme(themeId);
            mockMvc.perform(delete("/admin/themes/{id}", themeId)
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, adminToken)))
                    .andExpect(status().isNoContent());
        }

        @Test
        void should_fail_when_userRequestDeleteTheme() throws Exception {
            mockMvc.perform(delete("/admin/themes/{id}", themeId)
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, userToken)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void should_fail_when_unauthorizedRequestDeleteTheme() throws Exception {
            mockMvc.perform(delete("/admin/themes/{id}", themeId)
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, "")))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class CreateSchedule {

        @Test
        void should_success_when_adminRequestCreateSchedule() throws Exception {
            when(adminService.createSchedule(any())).thenReturn(scheduleId);
            mockMvc.perform(post("/admin/schedules")
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, adminToken))
                            .content(mapper.writeValueAsString(new ScheduleRequest(themeId, "2022-08-11", "13:00")))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        void should_fail_when_userRequestCreateSchedule() throws Exception {
            mockMvc.perform(post("/admin/schedules")
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, userToken)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void should_fail_when_unauthorizedRequestCreateSchedule() throws Exception {
            mockMvc.perform(post("/admin/schedules")
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, "")))
                    .andExpect(status().isUnauthorized());
        }
    }

    @Nested
    class DeleteSchedule {

        @Test
        void should_success_when_adminRequestDeleteSchedule() throws Exception {
            doNothing().when(adminService).deleteSchedule(scheduleId);
            mockMvc.perform(delete("/admin/schedules/{id}", scheduleId)
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, adminToken)))
                    .andExpect(status().isNoContent());
        }

        @Test
        void should_fail_when_userRequestDeleteSchedule() throws Exception {
            mockMvc.perform(delete("/admin/schedules/{id}", scheduleId)
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, userToken)))
                    .andExpect(status().isForbidden());
        }

        @Test
        void should_fail_when_unauthorizedRequestDeleteSchedule() throws Exception {
            mockMvc.perform(delete("/admin/schedules/{id}", scheduleId)
                            .cookie(new Cookie(JwtTokenProvider.ACCESS_TOKEN, "")))
                    .andExpect(status().isUnauthorized());
        }
    }
}