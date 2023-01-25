package nextstep.support.exception;

public class InvalidMemberException extends RuntimeException{
    public InvalidMemberException() {
        super("아이디 또는 패스워드가 잘못되었습니다.");
    }
}
