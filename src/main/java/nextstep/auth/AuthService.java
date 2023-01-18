package nextstep.auth;

import lombok.RequiredArgsConstructor;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.exception.ErrorCode;
import nextstep.exception.RoomEscapeException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public TokenResponse createToken(TokenRequest tokenRequest) {
        Member member = getMemberOrThrowException(tokenRequest.getUsername());
        checkPassword(member.getPassword(), tokenRequest.getPassword());

        return TokenResponse.of(
                jwtTokenProvider.createToken(tokenRequest.getUsername())
        );
    }

    private Member getMemberOrThrowException(String userName){
        Member member = memberDao.findByUsername(userName);
        if(ObjectUtils.isEmpty(member)) {
            throw new RoomEscapeException(ErrorCode.NO_SUCH_MEMBER);
        }

        return member;
    }

    private void checkPassword(String memberPassword, String inputPassword){
        if(!memberPassword.equals(inputPassword)){
            throw new RoomEscapeException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }

}
