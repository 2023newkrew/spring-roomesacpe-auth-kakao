package nextstep.reservation.service;

import nextstep.reservation.datamapper.ReservationMapper;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.entity.ReservationEntity;
import nextstep.reservation.repository.ReservationRepository;
import nextstep.schedule.entity.ScheduleEntity;
import nextstep.schedule.repository.ScheduleRepository;
import nextstep.support.DuplicateEntityException;
import nextstep.support.NotExistEntityException;
import nextstep.theme.dao.ThemeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final ThemeDao themeDao;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ScheduleRepository scheduleRepository, ThemeDao themeDao) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.themeDao = themeDao;
    }

    public Long create(Long scheduleId, Long memberId) {
        ScheduleEntity targetSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(NotExistEntityException::new);

        List<ReservationEntity> reservations = reservationRepository.findByScheduleId(scheduleId);
        if (!reservations.isEmpty()) {
            throw new DuplicateEntityException();
        }

        return reservationRepository.save(ReservationEntity.of(targetSchedule, memberId));
    }

    public List<ReservationResponse> findAllByThemeIdAndDate(Long themeId, String date) {
        Optional.ofNullable(themeDao.findById(themeId))
                .orElseThrow(NotExistEntityException::new);

        return reservationRepository.findAllByThemeIdAndDate(themeId, date)
                .stream()
                .map(ReservationMapper.INSTANCE::entityToDto)
                .collect(Collectors.toList())
                ;
    }

    public void delete(Long id, Long memberId) {
        if (!reservationRepository.existsByIdAndMemberId(id, memberId)) {
            throw new RuntimeException();
        }

        reservationRepository.deleteById(id);
    }
}
