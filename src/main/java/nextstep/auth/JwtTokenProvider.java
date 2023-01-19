package nextstep.auth;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.member-secret}")
    private String secretKey;

    @Value("${jwt.member-validity}")
    private Long validityInMilliseconds;

    @Value("${jwt.admin-secret}")
    private String adminSecretKey;

    @Value("${jwt.admin-validity}")
    private Long adminValidityInMilliseconds;

    public String createToken(String principal) {
        return createTokenWith(principal, validityInMilliseconds, secretKey);
    }

    public String createAdminToken(String principal) {
        return createTokenWith(principal, adminValidityInMilliseconds, adminSecretKey);
    }

    private String createTokenWith(String principal, Long validationTimeInMilliseconds, String secretKey) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validationTimeInMilliseconds);

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

    public boolean validateToken(String token) {
        return validateTokenWith(token, secretKey);
    }

    public boolean validateAdminToken(String token) {
        return validateTokenWith(token, adminSecretKey);
    }

    private boolean validateTokenWith(String token, String secretKey) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
