package nextstep.reservation;

import nextstep.exception.business.BusinessException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.schedule.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static nextstep.exception.business.BusinessErrorCode.DELETE_FAILED_WHEN_NOT_MY_RESERVATION;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    ReservationService reservationService;

    @Mock
    ReservationDao reservationDao;

    @Mock
    MemberDao memberDao;

    @Mock
    Schedule schedule;

    @Nested
    @DisplayName("예약 삭제 테스트")
    class DeleteReservation {

        private final String myName = "name";
        private final String otherName = "otherName";
        private final Reservation myReservation = new Reservation(schedule, myName);
        private final Member me = new Member("username", "password", myName, "phone");
        private final Member other = new Member("username", "password", otherName, "phone");

        @Test
        @DisplayName("내가 예약한 예약정보는 취소할 수 있다.")
        void should_successfully_when_myReservation() {
            when(reservationDao.findById(anyLong())).thenReturn(Optional.of(myReservation));
            when(memberDao.findById(anyLong())).thenReturn(Optional.of(me));
            doNothing().when(reservationDao).deleteById(anyLong());

            assertDoesNotThrow(() -> reservationService.cancel(1L, 1L));
        }

        @Test
        @DisplayName("내가 예약한 예약정보는 다른 사람이 취소할 수 없다.")
        void should_throwException_when_otherReservation() {
            when(reservationDao.findById(anyLong())).thenReturn(Optional.of(myReservation));
            when(memberDao.findById(anyLong())).thenReturn(Optional.of(other));

            assertThatThrownBy(() -> reservationService.cancel(1L, 1L))
                    .isInstanceOf(BusinessException.class)
                    .hasMessage(DELETE_FAILED_WHEN_NOT_MY_RESERVATION.getMessage());
        }
    }
}
