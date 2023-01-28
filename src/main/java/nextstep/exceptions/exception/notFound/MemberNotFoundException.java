package nextstep.exceptions.exception.notFound;

public class MemberNotFoundException extends ObjectNotFoundException {
    public MemberNotFoundException() {
        super("멤버를 찾을 수 없습니다.");
    }
}
