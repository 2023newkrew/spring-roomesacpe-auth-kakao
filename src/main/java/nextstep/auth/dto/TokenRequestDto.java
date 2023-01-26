package nextstep.auth.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public final class TokenRequestDto {

    private String username;
    private String password;
    private String role;
}
