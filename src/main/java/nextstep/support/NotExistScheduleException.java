package nextstep.support;

public class NotExistScheduleException extends RuntimeException {
    public NotExistScheduleException() {
        super("존재하지 않는 일정 입니다.");
    }
}
