package nextstep.member.mapper;

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

    default MemberResponse domainToResponseDto(Member member) {
        if (member == null) {
            return null;
        }

        Long id = member.getId();
        String username = member.getUsername();
        String password = member.getPassword();
        String name = member.getName();
        String phone = member.getPhone();
        String role = member.getRole();

        return new MemberResponse(id, username, password, name, phone, role);
    }
}
