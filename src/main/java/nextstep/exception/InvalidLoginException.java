package nextstep.exception;

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException() {
        super("사용자 이름 또는 패스워드가 잘못됐습니다.");
    }
}
