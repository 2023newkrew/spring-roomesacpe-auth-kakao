package nextstep.auth.service;


import nextstep.auth.dto.TokenResponse;
import nextstep.member.dao.MemberDao;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final MemberDao memberDao;

    public AuthServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public TokenResponse login(String username, String password) {

        return null;
    }
}
