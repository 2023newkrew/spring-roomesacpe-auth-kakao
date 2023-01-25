package nextstep.reservations.domain.service.auth;

import nextstep.reservations.domain.entity.member.Member;
import nextstep.reservations.domain.service.member.MemberService;
import nextstep.reservations.dto.auth.TokenReponseDto;
import nextstep.reservations.dto.auth.TokenRequestDto;
import nextstep.reservations.util.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberService memberService;

    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final MemberService memberService, final JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public TokenReponseDto createAccessToken(TokenRequestDto tokenRequestDto) {
        Member member = memberService.findByUsername(tokenRequestDto.getUsername());
        return new TokenReponseDto(jwtTokenProvider.createToken(member.getId().toString()));
    }
}
