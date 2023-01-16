package nextstep.support;

public class NotExistMemberException extends RoomEscapeException {
    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.NOT_EXISTS_MEMBER;
    }
}
