package nextstep.auth;

import nextstep.auth.role.Role;
import nextstep.exception.BusinessException;
import nextstep.exception.BusinessErrorCode;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static nextstep.member.Member.ADMIN_USERNAME;

@Service
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberDao memberDao;

    public AuthService(JwtTokenProvider jwtTokenProvider, MemberDao memberDao) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberDao = memberDao;
    }

    public String createToken(TokenRequest tokenRequest) {
        String username = tokenRequest.getUsername();
        String password = tokenRequest.getPassword();
        Member member = login(username, password);

        List<Role> roles = getRoles(username);

        return jwtTokenProvider.createToken(String.valueOf(member.getId()), roles);
    }

    private static List<Role> getRoles(String username) {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_USER);
        if (Objects.equals(username, ADMIN_USERNAME)) {
            roles.add(Role.ROLE_ADMIN);
        }
        return roles;
    }

    private Member login(String username, String password) {
        Member member = memberDao.findByUsername(username)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.MEMBER_NOT_FOUND));
        if (member.isWrongPassword(password)) {
            throw new BusinessException(BusinessErrorCode.LOGIN_FAILED_WRONG_USERNAME_PASSWORD);
        }
        return member;
    }
}
