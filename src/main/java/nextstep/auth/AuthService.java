package nextstep.auth;

import nextstep.admin.Admin;
import nextstep.admin.AdminDao;
import nextstep.auth.util.JwtTokenProvider;
import nextstep.error.ErrorCode;
import nextstep.exception.NotCorrectPasswordException;
import nextstep.exception.NotExistEntityException;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;
    private final AdminDao adminDao;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(MemberDao memberDao, AdminDao adminDao, JwtTokenProvider jwtTokenProvider) {
        this.memberDao = memberDao;
        this.adminDao = adminDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenResponse createMemberToken(TokenRequest tokenRequest) {
        Member member = memberDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(() -> new NotExistEntityException(ErrorCode.USER_NOT_FOUND));
        if(member.checkWrongPassword(tokenRequest.getPassword())) {
            throw new NotCorrectPasswordException(ErrorCode.UNAUTHORIZED, "잘못된 ID 또는 비밀번호입니다.");
        }
        String accessToken = jwtTokenProvider.createToken(member.getUsername());
        return new TokenResponse(accessToken);
    }

    public TokenResponse createAdminToken(TokenRequest tokenRequest) {
        Admin admin = adminDao.findByUsername(tokenRequest.getUsername())
                .orElseThrow(() -> new NotExistEntityException(ErrorCode.USER_NOT_FOUND));
        if(admin.checkWrongPassword(tokenRequest.getPassword())) {
            throw new NotCorrectPasswordException(ErrorCode.UNAUTHORIZED, "잘못된 ID 또는 비밀번호입니다.");
        }
        String accessToken = jwtTokenProvider.createToken(admin.getUsername());
        return new TokenResponse(accessToken);
    }
}
