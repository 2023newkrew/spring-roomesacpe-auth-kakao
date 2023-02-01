package nextstep.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

public class EncodedJwtToken {
    private static final String SECRET_KEY = "learning-test-spring-learning-test-spring";
    private static final Long validityInMilliseconds = 3600000L;

    private final String principal;

    public EncodedJwtToken(String principal) {
        this.principal = principal;
    }

    public String getRawToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(principal)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }
}
