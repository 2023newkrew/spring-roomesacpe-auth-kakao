package nextstep.support.excpetion;

public class NotQualifiedMemberException extends RuntimeException {
    public NotQualifiedMemberException() {
        super("접근 권한이 없는 멤버입니다.");
    }

    public NotQualifiedMemberException(String message) {
        super(message);
    }


    public NotQualifiedMemberException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotQualifiedMemberException(Throwable cause) {
        super(cause);
    }
}
