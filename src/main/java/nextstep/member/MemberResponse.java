package nextstep.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponse {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String role;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .name(member.getName())
                .phone(member.getPhone())
                .role(member.getRole())
                .build();
    }

}
