package nextstep.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final String secretKey = "lXH810veBaPOZAh5WN4cuo9BFRolED9ELuOuUa10lQCBcn0ckL";

    private final SecretKey key;

    private final JwtParser parserBuilder;

    private final long validityInMilliseconds = 3_600_000;

    public JwtTokenProvider() {
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        parserBuilder = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    public String createToken(String principal) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getPrincipal(String token) {
        return parserBuilder
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parserBuilder
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
