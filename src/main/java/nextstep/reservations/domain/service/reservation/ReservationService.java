package nextstep.reservations.domain.service.reservation;

import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
import nextstep.reservations.exceptions.reservation.exception.DuplicateReservationException;
import nextstep.reservations.exceptions.reservation.exception.NoSuchReservationException;
import nextstep.reservations.exceptions.theme.exception.NoSuchThemeException;
import nextstep.reservations.repository.reservation.ReservationRepository;
import nextstep.reservations.util.mapper.ReservationMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationService(final ReservationRepository reservationRepository, final ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public Long addReservation(final ReservationRequestDto requestDto) {
        try {
            return reservationRepository.add(reservationMapper.requestDtoToReservation(requestDto));
        }
        catch (DuplicateKeyException e) {
            throw new DuplicateReservationException();
        }
        catch (DataIntegrityViolationException e) {
            throw new NoSuchThemeException();
        }
    }

    public ReservationResponseDto getReservation(final Long id) {
        Reservation reservation;
        try {
            reservation = reservationRepository.findById(id);
        }
        catch (EmptyResultDataAccessException e) {
            throw new NoSuchReservationException();
        }
        if (reservation == null) throw new NoSuchReservationException();
        return reservationMapper.reservationToResponseDto(reservation);
    }

    public void deleteReservation(final Long id) {
        int removeCount = reservationRepository.remove(id);

        if (removeCount == 0) throw new NoSuchReservationException();
    }
}
