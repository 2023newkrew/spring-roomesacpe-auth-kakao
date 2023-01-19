package nextstep.support;

public class NotExistMemberException extends RuntimeException{
    public NotExistMemberException() {
        super("존재하지 않는 회원 입니다.");
    }
}
