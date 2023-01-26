package nextstep.member;

import static nextstep.common.exception.ExceptionMessage.DUPLICATED_USERNAME;
import static nextstep.common.exception.ExceptionMessage.NOT_EXIST_MEMBER;

import lombok.RequiredArgsConstructor;
import nextstep.common.exception.DuplicateEntityException;
import nextstep.common.exception.NotExistEntityException;
import nextstep.member.dto.MemberRequestDto;
import nextstep.member.dto.MemberResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;


    public Long create(MemberRequestDto memberRequestDto) {
        memberDao.findByUsername(memberRequestDto.getUsername())
            .ifPresent(m -> {
                throw new DuplicateEntityException(DUPLICATED_USERNAME.getMessage());
            });
        return memberDao.save(memberRequestDto.toEntity());
    }

    public MemberResponseDto findByUsername(final String username) {
        Member findUser = memberDao.findByUsername((username))
            .orElseThrow(() -> new NotExistEntityException(NOT_EXIST_MEMBER.getMessage()));
        return MemberResponseDto.toDto(findUser);
    }

}
