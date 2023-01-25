package nextstep.member.service;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import nextstep.error.ErrorCode;
import nextstep.error.exception.RoomReservationException;
import nextstep.member.domain.Member;
import nextstep.member.repository.MemberDao;
import nextstep.reservation.Reservation;
import nextstep.reservation.ReservationDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAdminService {

    private final MemberDao memberDao;

    private final ReservationDao reservationDao;

    public List<Member> findAll() {
        return memberDao.findAll();
    }

    public void deleteById(Long id) {
        Member member = memberDao.findById(id);
        if (Objects.isNull(member)) {
            throw new RoomReservationException(ErrorCode.MEMBER_NOT_FOUND);
        }
        List<Reservation> reservations = reservationDao.findByMemberId(id);
        if (reservations.size() > 0) {
            throw new RoomReservationException(ErrorCode.MEMBER_CANT_BE_DELETED);
        }
        int deletedCount = memberDao.deleteById(id);
        if (deletedCount != 1) {
            throw new RoomReservationException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }
}
