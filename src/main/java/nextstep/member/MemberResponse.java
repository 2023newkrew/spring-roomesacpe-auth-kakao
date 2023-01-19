package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MemberResponse {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone()).build();
    }
}
