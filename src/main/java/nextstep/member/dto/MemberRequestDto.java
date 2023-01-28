package nextstep.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.common.ValidMemberRole;
import nextstep.member.Member;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class MemberRequestDto {

    @NotBlank(message = "사용할 이름은 공백일 수 없습니다.")
    private String username;
    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;
    @NotBlank(message = "실명은 공백일 수 없습니다.")
    private String name;
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "올바른 전화번호 양식이 아닙니다.")
    private String phone;
    @ValidMemberRole
    private String role;

    public Member toEntity() {
        return new Member(username, password, name, phone, role);
    }
}
