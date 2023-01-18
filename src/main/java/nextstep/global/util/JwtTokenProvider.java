package nextstep.global.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public final class JwtTokenProvider {

    private static final String secretKey = "lXH810veBaPOZAh5WN4cuo9BFRolED9ELuOuUa10lQCBcn0ckL";
    private static final SecretKey key;
    private static final JwtParser parserBuilder;
    private static final long validityInMilliseconds = 3_600_000;

    static {
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        parserBuilder = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    private JwtTokenProvider() {
    }

    public static String createToken(String principal) {
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

    public static String getPrincipal(String token) {

        return parserBuilder
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            parserBuilder
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
