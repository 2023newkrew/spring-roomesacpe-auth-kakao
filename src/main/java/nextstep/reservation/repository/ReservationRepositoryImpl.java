package nextstep.reservation.repository;

import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.entity.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    public Long save(ReservationEntity reservationEntity) {

        return reservationDao.save(reservationEntity);
    }

    @Override
    public List<ReservationEntity> findAllByThemeIdAndDate(Long themeId, String date) {

        return Optional.ofNullable(reservationDao.findAllByThemeIdAndDate(themeId, date))
                .orElse(new ArrayList<>())
                ;
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {

        return Optional.ofNullable(reservationDao.findById(id));
    }

    @Override
    public List<ReservationEntity> findByScheduleId(Long scheduleId) {

        return Optional.ofNullable(reservationDao.findByScheduleId(scheduleId))
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
