package nextstep.member;

public class LoginMember {
    private Long id;

    public LoginMember() {
    }

    public LoginMember(Member member) {
        this.id = member.getId();
    }

    public Long getId() {
        return id;
    }
}
