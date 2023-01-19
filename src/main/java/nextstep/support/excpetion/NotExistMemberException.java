package nextstep.support.excpetion;

public class NotExistMemberException extends NotExistEntityException {
    public NotExistMemberException() {
        super("존재하지 않는 멤버입니다.");
    }

    public NotExistMemberException(String message) {
        super(message);
    }


    public NotExistMemberException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotExistMemberException(Throwable cause) {
        super(cause);
    }
}
