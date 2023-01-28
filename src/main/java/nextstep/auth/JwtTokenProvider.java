package nextstep.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nextstep.member.Member;
import nextstep.member.MemberDao;
import nextstep.support.excpetion.NotExistMemberException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey = "learning-test-spring";
    private long validityInMilliseconds = 3600000;

    private final MemberDao memberDao;

    public JwtTokenProvider(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public String createToken(String principal) {
        Member member = memberDao.findByUsername(principal)
                .orElseThrow(NotExistMemberException::new);
        return createToken(principal, member.getId());
    }

    public String createToken(String username, Long memberId) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("id", memberId.toString());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String getPrincipal(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getMemberId(String token) {
        return Long.parseLong((String)Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id"));
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
