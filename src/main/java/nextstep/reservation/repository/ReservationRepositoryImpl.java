package nextstep.reservation.repository;

import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.mapper.ReservationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;

    @Autowired
    public ReservationRepositoryImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Long save(Reservation reservation) {

        return reservationDao.save(ReservationMapper.INSTANCE.domainToEntity(reservation));
    }

    @Override
    public Optional<Reservation> findById(Long id) {

        return Optional.ofNullable(ReservationMapper.INSTANCE.entityToDomain(reservationDao.findById(id)));
    }

    @Override
    public List<Reservation> findByScheduleId(Long scheduleId) {

        return ReservationMapper.INSTANCE.entityListToDomainList(reservationDao.findByScheduleId(scheduleId));
    }

    @Override
    public int deleteById(Long id) {

        return reservationDao.deleteById(id);
    }
}
