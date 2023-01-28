package nextstep.schedule.service;

import nextstep.global.exception.NotExistEntityException;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.repository.ScheduleRepository;
import nextstep.theme.domain.Theme;
import nextstep.theme.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    public Long create(Long themeId, LocalDate date, LocalTime time) {
        Theme targetTheme = themeRepository.findById(themeId)
                .orElseThrow(NotExistEntityException::new);

        return scheduleRepository.save(Schedule.of(targetTheme.getId(), date, time));
    }

    public List<Schedule> findByThemeIdAndDate(Long themeId, String date) {

        return scheduleRepository.findByThemeIdAndDate(themeId, date);
    }

    public void deleteById(Long id) {
        if (scheduleRepository.deleteById(id) == 0) {
            throw new NotExistEntityException();
        }
    }
}
