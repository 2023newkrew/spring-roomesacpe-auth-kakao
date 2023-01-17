package nextstep.auth.service;

import nextstep.auth.JwtTokenProvider;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.domain.MemberForAuth;
import nextstep.member.domain.MemberWithId;
import nextstep.member.repository.MemberRepository;
import nextstep.support.NotExistEntityException;
import nextstep.support.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final MemberRepository memberRepository;

    @Autowired
    public AuthService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public TokenResponse login(String username, String password) {
        MemberForAuth requestedMember = new MemberForAuth(username, password);

        MemberWithId realMember = memberRepository.findByUsername(requestedMember.getUsername())
                .orElseThrow(NotExistEntityException::new);
        if (!realMember.matchPassword(requestedMember.getPassword())) {
            throw new UnauthenticatedException();
        }

        return new TokenResponse(JwtTokenProvider.createToken(String.valueOf(realMember.getId())));
    }
}
