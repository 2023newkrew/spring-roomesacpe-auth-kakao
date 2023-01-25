package nextstep.reservations.dto.reservation;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeTable {
    public static final ArrayList<LocalTime> values;

    static {
        values = Stream.of("09:00", "11:00", "13:00", "15:00", "17:00", "19:00", "21:00")
                .map(LocalTime::parse).collect(Collectors.toCollection(ArrayList::new));
    }
}
