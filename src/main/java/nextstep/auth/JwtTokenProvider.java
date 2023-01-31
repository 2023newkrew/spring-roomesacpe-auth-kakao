package nextstep.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

import static nextstep.auth.JwtTokenConfig.SECRET_KEY;
import static nextstep.auth.JwtTokenConfig.VALIDITY_IN_MILLISECONDS;

@Component
public class JwtTokenProvider {
    public String createToken(String principal) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getPrincipal(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
