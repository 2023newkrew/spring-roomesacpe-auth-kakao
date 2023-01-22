package nextstep.auth.service;

import nextstep.auth.domain.AccessToken;
import nextstep.member.mapper.MemberMapper;
import nextstep.member.domain.Member;
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

    public AccessToken login(String username, String password) {
        Member requestedMember = Member.builder()
                .username(username)
                .password(password)
                .build();

        Member existMember = MemberMapper.INSTANCE.entityToDomain(memberRepository.findByUsername(requestedMember.getUsername())
                .orElseThrow(NotExistEntityException::new));
        if (!existMember.matchPassword(requestedMember)) {
            throw new UnauthenticatedException();
        }

        return AccessToken.create(String.valueOf(existMember.getId()));
    }
}
