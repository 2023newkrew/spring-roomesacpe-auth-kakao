package nextstep.support;

public class NoSuchMemberException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NO_SUCH_MEMBER;
    }
}
