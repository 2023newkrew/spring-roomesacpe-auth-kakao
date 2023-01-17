package nextstep.auth.service;

import nextstep.auth.JwtTokenProvider;
import nextstep.auth.dto.TokenResponse;
import nextstep.member.domain.Member;
import nextstep.member.domain.MemberForAuth;
import nextstep.member.repository.MemberRepository;
import nextstep.support.NotExistEntityException;
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
        requestedMember.encryptPassword();

        Member realMember = memberRepository.findByUsernameAndPassword(requestedMember)
                .orElseThrow(NotExistEntityException::new);

        // TODO: 원래는 id를 넣었었는데 도메인으로 변경되며 임시로 username으로 바꾼거라 수정해야함!
        return new TokenResponse(JwtTokenProvider.createToken(String.valueOf(realMember.getUsername())));
    }
}
