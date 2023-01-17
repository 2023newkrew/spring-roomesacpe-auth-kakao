package nextstep.infrastructure.jwt;

import lombok.*;
import nextstep.infrastructure.template.Role;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetails {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private Role role;
}
