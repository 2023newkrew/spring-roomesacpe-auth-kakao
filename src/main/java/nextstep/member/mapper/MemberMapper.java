package nextstep.member.mapper;

import lombok.AllArgsConstructor;
import nextstep.global.util.PasswordUtility;
import nextstep.member.dto.MemberRequest;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MemberMapper {
    private PasswordUtility passwordUtility;

    public MemberEntity requestToEntity(MemberRequest memberRequest) {
        return new MemberEntity(null,
                memberRequest.getUsername(),
                passwordUtility.encrypt(memberRequest.getPassword()),
                memberRequest.getName(),
                memberRequest.getPhone());
    }

    public MemberResponse entityToResponse(MemberEntity memberEntity) {
        return new MemberResponse(
                memberEntity.getId(),
                memberEntity.getUsername(),
                memberEntity.getPassword(),
                memberEntity.getName(),
                memberEntity.getPhone()
        );
    }
}
