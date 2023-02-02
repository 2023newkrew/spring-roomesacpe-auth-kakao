package nextstep.exception;

public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException() {
        super("해당 역할은 존재하지 않습니다.");
    }
}
