package nextstep.exceptions.exception.duplicate;

public class DuplicatedScheduleException extends DuplicatedException {

    public DuplicatedScheduleException() {
        super("중복된 스케줄이 존재합니다.");
    }
}
