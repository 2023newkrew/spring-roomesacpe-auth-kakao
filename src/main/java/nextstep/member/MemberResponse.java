package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberResponse {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getId(), member.getUsername(), member.getPassword(), member.getName(),
                member.getPhone());
    }
}
