package nextstep.schedule.service;

import nextstep.schedule.datamapper.ScheduleMapper;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.schedule.dto.ScheduleResponse;
import nextstep.schedule.entity.ScheduleEntity;
import nextstep.schedule.repository.ScheduleRepository;
import nextstep.support.NotExistEntityException;
import nextstep.theme.dao.ThemeDao;
import nextstep.theme.entity.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ThemeDao themeDao;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, ThemeDao themeDao) {
        this.scheduleRepository = scheduleRepository;
        this.themeDao = themeDao;
    }

    public Long create(ScheduleRequest scheduleRequest) {
        Theme theme = themeDao.findById(scheduleRequest.getThemeId());

        return scheduleRepository.save(ScheduleEntity.of(theme, LocalDate.parse(scheduleRequest.getDate()), LocalTime.parse(scheduleRequest.getTime())));
    }

    public List<ScheduleResponse> findByThemeIdAndDate(Long themeId, String date) {
        List<ScheduleEntity> targetSchedules = scheduleRepository.findByThemeIdAndDate(themeId, date);

        return targetSchedules.stream()
                .map(ScheduleMapper.INSTANCE::entityToDtoResponse)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new NotExistEntityException();
        }

        scheduleRepository.deleteById(id);
    }
}
