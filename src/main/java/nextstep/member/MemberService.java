package nextstep.member;

import lombok.RequiredArgsConstructor;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.member.dto.MemberRequestDto;
import nextstep.member.dto.MemberResponseDto;
import nextstep.support.exception.DuplicateEntityException;
import nextstep.support.exception.NotExistEntityException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;


    public Long create(MemberRequestDto memberRequestDto) {
        memberDao.findByUsername(memberRequestDto.getUsername())
            .orElseThrow(() -> new DuplicateEntityException("같은 username을 가진 회원이 존재합니다."));
        return memberDao.save(memberRequestDto.toEntity());
    }

    public MemberResponseDto findByUsername(String username) {
        Member findUser = memberDao.findByUsername((username))
            .orElseThrow(() -> new NotExistEntityException("존재하지 않는 회원입니다."));
        return MemberResponseDto.toDto(findUser);
    }

    public void validateUsernameAndPassword(TokenRequestDto tokenRequestDto) {
        memberDao.findByUsernameAndPassword(tokenRequestDto.getUsername(),
                tokenRequestDto.getPassword())
            .orElseThrow(() -> new NotExistEntityException("해당 회원정보를 가진 회원이 없습니다."));
    }
}
