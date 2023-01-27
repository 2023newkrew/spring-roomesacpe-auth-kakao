package nextstep.member.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.member.entity.MemberEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String phone;
}
