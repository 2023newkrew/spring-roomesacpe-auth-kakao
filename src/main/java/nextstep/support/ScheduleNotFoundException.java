package nextstep.support;

public class ScheduleNotFoundException extends NotFoundException {
    public ScheduleNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "조건에 맞는 스케줄을 찾을 수 없습니다.";
    }
}
