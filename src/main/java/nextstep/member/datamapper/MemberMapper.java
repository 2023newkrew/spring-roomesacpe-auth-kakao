package nextstep.member.datamapper;

import nextstep.member.domain.Member;
import nextstep.member.dto.MemberResponse;
import nextstep.member.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member entityToDomain(MemberEntity memberEntity);

    @Mapping(target = "id", ignore = true)
    MemberEntity domainToEntity(Member member);

    default MemberResponse entityToResponseDto(MemberEntity memberEntity) {
        if (memberEntity == null) {
            return null;
        }

        Long id = memberEntity.getId();
        String username = memberEntity.getUsername();
        String password = memberEntity.getPassword();
        String name = memberEntity.getName();
        String phone = memberEntity.getPhone();

        return new MemberResponse(id, username, password, name, phone);
    }
}
