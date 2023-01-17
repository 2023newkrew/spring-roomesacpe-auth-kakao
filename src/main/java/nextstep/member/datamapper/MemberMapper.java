package nextstep.member.datamapper;

import nextstep.member.domain.Member;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member entityToDomain(MemberEntity memberEntity);

    MemberEntity domainToEntity(Member member);

    MemberResponse domainToResponseDto(Member member);
}
