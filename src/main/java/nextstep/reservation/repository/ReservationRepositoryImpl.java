package nextstep.reservation.repository;

import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.datamapper.ReservationMapper;
import nextstep.reservation.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {

        return Optional.ofNullable(reservationDao.findAllByThemeIdAndDate(themeId, date))
                .map(item -> item.stream().map(ReservationMapper.INSTANCE::entityToDomain).collect(Collectors.toList()))
                .orElse(new ArrayList<>())
                ;
    }

    @Override
    public Optional<Reservation> findById(Long id) {

        return Optional.ofNullable(reservationDao.findById(id))
                .map(ReservationMapper.INSTANCE::entityToDomain);
    }

    @Override
    public List<Reservation> findByScheduleId(Long scheduleId) {

        return Optional.ofNullable(reservationDao.findByScheduleId(scheduleId))
                .map(item -> item.stream().map(ReservationMapper.INSTANCE::entityToDomain).collect(Collectors.toList()))
                .orElse(new ArrayList<>())
                ;
    }

    @Override
    public boolean existsByIdAndMemberId(Long id, Long memberId) {

        return reservationDao.existsByIdAndMemberId(id, memberId);
    }

    @Override
    public int deleteById(Long id) {

        return reservationDao.deleteById(id);
    }
}
