package nextstep.schedule.entity;

import lombok.Getter;
import nextstep.theme.entity.ThemeEntity;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class ScheduleEntity {

    private final Long id;
    private final AggregateReference<ThemeEntity, Long> themeId;
    private final LocalDate date;
    private final LocalTime time;

    private ScheduleEntity(Long id, AggregateReference<ThemeEntity, Long> themeId, LocalDate date, LocalTime time) {
        this.id = id;
        this.themeId = themeId;
        this.date = date;
        this.time = time;
    }

    public ScheduleEntity(Long id, Long themeId, LocalDate date, LocalTime time) {
        this(id, AggregateReference.to(themeId), date, time);
    }

    public Long getThemeId() {
        return themeId.getId();
    }
}
