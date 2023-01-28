package nextstep.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class DecodedJwtToken {
    private static final String secretKey = "learning-test-spring-learning-test-spring";
    private final String rawToken;

    public DecodedJwtToken(String rawToken) {
        this.rawToken = rawToken;
    }

    public Long getPrincipal() {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parse(rawToken,
                        new JwtHandlerAdapter<>() {
                            @Override
                            public Long onClaimsJws(Jws<Claims> jws) {
                                return Long.parseLong(jws.getBody()
                                        .getSubject());
                            }
                        });
    }
}
