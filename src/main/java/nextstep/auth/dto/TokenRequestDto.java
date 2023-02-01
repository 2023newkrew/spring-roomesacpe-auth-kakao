package nextstep.auth.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nextstep.common.ValidLoginRequest;
import nextstep.common.ValidMemberRole;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ValidLoginRequest
public final class TokenRequestDto {

    @NotBlank(message = "사용자 이름은 빈칸이 될 수 없습니다.")
    private String username;
    @NotBlank(message = "비밀번호는 빈칸이 될 수 없습니다.")
    private String password;
    @ValidMemberRole
    private String role;
}
