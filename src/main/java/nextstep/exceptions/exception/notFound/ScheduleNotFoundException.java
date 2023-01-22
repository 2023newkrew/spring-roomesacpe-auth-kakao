package nextstep.exceptions.exception.notFound;

public class ScheduleNotFoundException extends ObjectNotFoundException {
    public ScheduleNotFoundException() {
        super("스케쥴을 찾을 수 없습니다.");
    }
}
