package nextstep.support;

public class InvalidTokenRequestException extends RuntimeException {

    public InvalidTokenRequestException() {
        super("사용자 이름 또는 패스워드가 잘못됐습니다.");
    }
}
