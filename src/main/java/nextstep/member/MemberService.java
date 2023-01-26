package nextstep.member;

import static nextstep.common.exception.ExceptionMessage.DUPLICATED_USERNAME;
import static nextstep.common.exception.ExceptionMessage.NOT_EXIST_MEMBER;
import static nextstep.common.exception.ExceptionMessage.WRONG_PASSWORD;

import lombok.RequiredArgsConstructor;
import nextstep.auth.dto.TokenRequestDto;
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

    public MemberResponseDto findByUsername(String username) {
        Member findUser = memberDao.findByUsername((username))
            .orElseThrow(() -> new NotExistEntityException(NOT_EXIST_MEMBER.getMessage()));
        return MemberResponseDto.toDto(findUser);
    }

    public void validateUsernameAndPassword(TokenRequestDto tokenRequestDto) {
        Member findUser = memberDao.findByUsername(tokenRequestDto.getUsername())
            .orElseThrow(() -> new NotExistEntityException(NOT_EXIST_MEMBER.getMessage()));
        if (findUser.isMyPassword(tokenRequestDto.getPassword())) {
            throw new NotExistEntityException(WRONG_PASSWORD.getMessage());
        }
    }
}
