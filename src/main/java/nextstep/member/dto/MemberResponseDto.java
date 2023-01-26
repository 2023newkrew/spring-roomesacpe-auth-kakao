package nextstep.member.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nextstep.member.Member;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public final class MemberResponseDto {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    public static MemberResponseDto toDto(Member member) {
        return new MemberResponseDto(member.getId(), member.getUsername(), member.getPassword(),
            member.getName(), member.getPhone());
    }
}
