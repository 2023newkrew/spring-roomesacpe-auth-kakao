package nextstep.reservation;

import nextstep.exception.InaccessibleReservationException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.member.Role;
import nextstep.schedule.Schedule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
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
        private final Member me = new Member("username", "password", myName, "phone", Role.USER);
        private final Member other = new Member("username", "password", otherName, "phone", Role.USER);

        @Test
        @DisplayName("내가 예약한 예약정보는 취소할 수 있다.")
        void should_successfully_when_myReservation() {
            when(reservationDao.findById(any())).thenReturn(myReservation);
            when(memberDao.findById(any())).thenReturn(me);
            doNothing().when(reservationDao).deleteById(any());

            assertDoesNotThrow(() -> reservationService.deleteById(1L, 1L));
        }

        @Test
        @DisplayName("내가 예약한 예약정보는 다른 사람이 취소할 수 없다.")
        void should_throwException_when_otherReservation() {
            when(reservationDao.findById(any())).thenReturn(myReservation);
            when(memberDao.findById(any())).thenReturn(other);

            assertThatThrownBy(() -> reservationService.deleteById(1L, 1L))
                    .isInstanceOf(InaccessibleReservationException.class);
        }
    }
}