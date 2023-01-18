package nextstep.support;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "조건에 맞는 회원을 찾을 수 없습니다.";
    }
}
