package nextstep.member;

public class LoginMember extends MemberBaseDto {
    public LoginMember(Long id, String username, String password, String name, String phone) {
        super(id, username, password, name, phone);
    }

    public static LoginMember of(Member member) {
        return new LoginMember(member.getId(), member.getUsername(), member.getPassword(),
                member.getName(), member.getPhone());
    }
}
