package nextstep.support;

public class ThemeNotFoundException extends NotFoundException {
    public ThemeNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "조건에 맞는 테마를 찾을 수 없습니다.";
    }
}
