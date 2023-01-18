package nextstep.domain.context;

import lombok.*;
import nextstep.domain.model.template.Role;

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
