package nextstep.reservation;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;

public class ReservationValidator {
    private final ReservationRepository reservationRepository;

    public ReservationValidator(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void validateForDelete(Long memberId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId);
        if (!memberId.equals(reservation.getMember().getId())) {
            throw new AuthenticationException(ErrorCode.INVALID_TOKEN);
        }
    }
}
