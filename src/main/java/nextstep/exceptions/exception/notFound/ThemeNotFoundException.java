package nextstep.exceptions.exception.notFound;

public class ThemeNotFoundException extends ObjectNotFoundException {
    public ThemeNotFoundException() {
        super("테마를 찾을 수 없습니다.");
    }
}
