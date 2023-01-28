package nextstep.exceptions.exception.duplicate;

public class DuplicatedThemeException extends DuplicatedException {

    public DuplicatedThemeException() {
        super("중복된 테마가 존재합니다");
    }
}
