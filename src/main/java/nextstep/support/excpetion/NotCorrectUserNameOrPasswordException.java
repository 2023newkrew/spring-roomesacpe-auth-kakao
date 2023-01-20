package nextstep.support.excpetion;

public class NotCorrectUserNameOrPasswordException extends RuntimeException {
    public NotCorrectUserNameOrPasswordException() {
        super("유저네임 혹은 패스워드가 일치하지 않습니다.");
    }

    public NotCorrectUserNameOrPasswordException(String message) {
        super(message);
    }


    public NotCorrectUserNameOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }


    public NotCorrectUserNameOrPasswordException(Throwable cause) {
        super(cause);
    }
}
