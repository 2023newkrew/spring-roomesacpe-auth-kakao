package nextstep.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nextstep.auth.dto.TokenRequest;
import nextstep.exceptions.exception.auth.AuthorizationException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secretKey = "learning-test-spring";
    private final long validityInMilliseconds = 3600000;

    public String createToken(TokenRequest tokenRequest) {
        Claims claims = Jwts.claims().setSubject(tokenRequest.getId());
        claims.put("role", tokenRequest.getRole());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getSubject(String token) {
        return getClaim(token).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            return !getClaim(token).getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthorizationException();
        }
    }

    private Claims getClaim(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getRole(String token) {
        return (String) getClaim(token).get("role");
    }

}
