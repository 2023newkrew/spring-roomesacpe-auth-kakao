package nextstep.support;

public class NoSuchThemeException extends RoomEscapeException{
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_THEME;
    }
}
