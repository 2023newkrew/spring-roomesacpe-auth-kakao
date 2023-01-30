package nextstep.member.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String name;
    private String phone;
    private String role;
}
