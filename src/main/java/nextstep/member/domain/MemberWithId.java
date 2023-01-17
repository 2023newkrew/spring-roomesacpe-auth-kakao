package nextstep.member.domain;

import lombok.Getter;

@Getter
public class MemberWithId extends Member {

    private final Long id;

    public MemberWithId(String username, String password, String name, String phone, Long id) {
        super(username, password, name, phone);
        this.id = id;
    }

    public static MemberWithId with(Member member, Long id) {
        return new MemberWithId(member.username, member.password, member.name, member.phone, id);
    }

    public Member toMember() {
        return new Member(this.username, this.password, this.name, this.phone);
    }
}
