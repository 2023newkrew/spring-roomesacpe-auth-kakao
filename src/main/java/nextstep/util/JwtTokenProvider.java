package nextstep.util;

import io.jsonwebtoken.*;
import nextstep.persistence.member.Role;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private final String secretKey = "learning-test-spring";

    public String createToken(String principal, Role role) {
        final long validityInMilliseconds = 3_600_000;

        Claims claims = Jwts.claims().setSubject(principal);
        claims.put("role", role.get());
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

    public String get(String token, String claim) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(claim).toString();
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
