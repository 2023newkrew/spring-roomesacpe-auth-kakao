package nextstep.member;

public class LoginMember extends MemberBaseDto{
    public LoginMember(Long id, String username, String name) {
        super(id, username, name);
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getUsername(), member.getName());
    }
}
