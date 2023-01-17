package nextstep.auth.service;

import nextstep.auth.JwtTokenProvider;
import nextstep.auth.dto.AuthRequest;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.dao.MemberDao;
import nextstep.member.entity.Member;
import nextstep.support.NotExistEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberDao memberDao;

    @Autowired
    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public TokenResponse login(AuthRequest authRequest) {
        Member member = memberDao.findByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword())
                .orElseThrow(NotExistEntityException::new);
        return new TokenResponse(JwtTokenProvider.createToken(String.valueOf(member.getId())));
    }
}
