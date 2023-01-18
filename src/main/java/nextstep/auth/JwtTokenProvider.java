package nextstep.auth;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private String secretKey = "learning-test-spring";
    private long validityInMilliseconds = 3600000;

    public String createToken(TokenData tokenData) {
        Claims claims = Jwts.claims();
        claims.put("id", tokenData.getId());
        claims.put("role", tokenData.getRole());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Long getId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return !claims.getBody()
                    .getExpiration()
                    .before(new Date());
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getValidToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new AuthenticationException();
        }

        var accessToken = bearerToken.substring(7);

        if (!validateToken(accessToken)) {
            throw new AuthenticationException();
        }

        return accessToken;

    }
}
