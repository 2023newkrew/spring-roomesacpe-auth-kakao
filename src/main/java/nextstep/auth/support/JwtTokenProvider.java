package nextstep.auth.support;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Long validityInMilliseconds;

    public String createCredential(String subject) {
        Claims claims = makeClaims(subject);
        Date expiration = getExpiration();
        Date issuedAt = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    private static Claims makeClaims(String subject) {
        return Jwts.claims().setSubject(subject);
    }

    private Date getExpiration() {
        Date now = new Date();
        return new Date(now.getTime() + validityInMilliseconds);
    }

    public String getSubject(String token) {
        String credential = getCredential(token);
        return getClaims(credential)
                        .getBody()
                        .getSubject();
    }

    public String getCredential(String token) {
        try{
            return token.split(" ")[1];
        }catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

    public boolean isValidToken(String token){
        String credential = getCredential(token);
        return isValidCredential(credential);
    }

    private boolean isValidCredential(String credential) {
        try {
            return !getClaims(credential)
                    .getBody()
                    .getExpiration()
                    .before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> getClaims(String credential) {
        try{
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(credential);
        }catch (Exception e) {
            throw new InvalidTokenException();
        }
    }
}
