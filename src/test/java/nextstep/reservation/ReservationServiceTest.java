package nextstep.reservation;

import nextstep.schedule.Schedule;
import nextstep.support.exception.NotUserOwnReservationException;
import nextstep.support.exception.NotExistReservationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReservationServiceTest {
    @Mock
    ReservationDao reservationDao;
    @InjectMocks
    ReservationService reservationService;

    @Test
    @DisplayName("존재하지 않는 예약 삭제 불가 테스트")
    void deleteNotExistReservationTest() {
        when(reservationDao.findById(anyLong())).thenReturn(null);
        assertThatThrownBy(() -> reservationService.deleteById(1L, "username"))
                .isInstanceOf(NotExistReservationException.class);
    }

    @Test
    @DisplayName("권한이 없는 예약 삭제 불가 테스트")
    void deleteUnauthorizedReservationTest() {
        Reservation reservation = new Reservation(1L, new Schedule(), "differentUsername");
        when(reservationDao.findById(anyLong())).thenReturn(reservation);
        assertThatThrownBy(() -> reservationService.deleteById(1L, "username"))
                .isInstanceOf(NotUserOwnReservationException.class);
    }

    @Test
    @DisplayName("예약 삭제 성공 테스트")
    void deleteTest() {
        Reservation reservation = new Reservation(1L, new Schedule(), "username");
        when(reservationDao.findById(anyLong())).thenReturn(reservation);
        assertThatCode(() -> reservationService.deleteById(1L, "username")).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("관리자 예약 삭제. 관리자는 모든 예약을 삭제할 수 있다.")
    void deleteAdminTest() {
        Reservation reservation = new Reservation(1L, new Schedule(), "username");
        when(reservationDao.findById(anyLong())).thenReturn(reservation);
        assertThatCode(() -> reservationService.deleteById(1L)).doesNotThrowAnyException();
    }
}
