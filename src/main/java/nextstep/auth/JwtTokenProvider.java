package nextstep.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static nextstep.auth.JwtTokenConfig.*;

@Component
public class JwtTokenProvider {
    public String createToken(String principal) {
        return createToken(principal, Map.of());
    }

    public String createToken(String principal, Map<String, String> privateClaims) {
        Claims claims = Jwts.claims().setSubject(principal);
        claims.putAll(privateClaims);
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

    public Optional<String> getRole(String token) {
        return Optional.ofNullable((String) Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody()
                .getOrDefault(TOKEN_ROLE_KEY, null));
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
