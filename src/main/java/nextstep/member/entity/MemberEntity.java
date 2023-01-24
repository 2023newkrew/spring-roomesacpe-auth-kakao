package nextstep.member.entity;

import com.fasterxml.jackson.annotation.JsonTypeId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nextstep.member.dto.MemberRequest;
import org.springframework.context.annotation.Conditional;

@Getter
@AllArgsConstructor
public class MemberEntity {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
}
