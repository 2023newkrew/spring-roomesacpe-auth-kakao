package nextstep.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String createToken(UserPrincipal principal) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        Claims claims = Jwts.claims()
                .setSubject(principal.getUsername())
                .setIssuedAt(now)
                .setExpiration(validity);
        claims.put("role", principal.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public UserPrincipal getPrincipal(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return new UserPrincipal(claims.getSubject(), (String) claims.get("role"));
    }

    public String parseTokenFromHeader(String token) {
        return Optional.ofNullable(token)
                .map(a -> a.split(" "))
                .filter(b -> b.length == 2)
                .map(split -> split[1])
                .orElse(null);
    }

    public boolean validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration()
                    .after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
