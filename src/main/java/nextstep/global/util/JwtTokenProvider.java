package nextstep.global.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.SignatureException;
import java.util.Date;
import nextstep.global.exception.ExpiredTokenException;
import nextstep.global.exception.MalformedTokenException;
import nextstep.global.exception.SecretKeyMismatchException;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    private String secretKey = "learning-test-spring";
    private long validityInMilliseconds = 3_600_000;

    public String createToken(String principal) {
        Claims claims = Jwts.claims().setSubject(principal);
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

    public void validateToken(String token) {
        Jws<Claims> jwt = tokenToJws(token);

        validateJwsExpiration(jwt);
    }

    private Jws<Claims> tokenToJws(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (IllegalArgumentException | MalformedJwtException e) {
            throw new MalformedTokenException();
        } catch (SignatureException e) {
            throw new SecretKeyMismatchException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }

    private void validateJwsExpiration(Jws<Claims> claims) {
        if (claims.getBody().getExpiration().before(new Date())) {
            throw new ExpiredTokenException();
        }
    }
}
