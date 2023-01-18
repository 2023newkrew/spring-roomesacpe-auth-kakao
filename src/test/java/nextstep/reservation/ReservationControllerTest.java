package nextstep.reservation;

import nextstep.auth.JwtTokenProvider;
import nextstep.exception.InaccessibleReservationException;
import nextstep.exception.UnAuthorizationException;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
@ExtendWith(MockitoExtension.class)
@Import(JwtTokenProvider.class)
class ReservationControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    ReservationService reservationService;

    @Autowired
    MockMvc mockMvc;

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    @DisplayName("토큰을 사용해 예약 삭제 테스트")
    class DeleteReservation {

        private Long userId = 1L;
        private String userToken = "Bearer " + jwtTokenProvider.createToken(String.valueOf(userId));
        private Long reservationId = 1L;

        @Test
        @DisplayName("유효한 토큰이고, 자신의 예약일 경우 204를 응답해야 한다.")
        void should_successfully_when_validRequest() throws Exception {
            doNothing().when(reservationService).deleteById(userId, reservationId);
            mockMvc.perform(delete("/reservations/" + reservationId)
                            .header("authorization", userToken))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("유효한 토큰이고, 자신의 예약이 아닐 경우 401을 응답해야 한다.")
        void should_401UnAuthorization_when_notMyReservation() throws Exception {
            doThrow(InaccessibleReservationException.class).when(reservationService).deleteById(userId, reservationId);
            mockMvc.perform(delete("/reservations/" + reservationId)
                            .header("authorization", userToken))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("유효한 토큰이 아닐 경우 401을 응답해야 한다.")
        void should_401UnAuthorization_when_invalidToken() throws Exception {
            doThrow(InaccessibleReservationException.class).when(reservationService).deleteById(userId, reservationId);
            mockMvc.perform(delete("/reservations/" + reservationId)
                            .header("authorization", ""))
                    .andExpect(status().isUnauthorized());
        }
    }
}