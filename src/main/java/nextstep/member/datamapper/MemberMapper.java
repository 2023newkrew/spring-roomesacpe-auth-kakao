package nextstep.member.datamapper;

import nextstep.member.domain.Member;
import nextstep.member.domain.MemberWithId;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member entityToDomain(MemberEntity memberEntity);

    MemberWithId entityToDomainWithId(MemberEntity memberEntity);

    default MemberEntity domainToEntity(Member member) {
        if (member == null) {
            return null;
        }

        return new MemberEntity(
                null,
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getPhone()
        );
    }

    MemberResponse domainToResponseDto(Member member);
}
