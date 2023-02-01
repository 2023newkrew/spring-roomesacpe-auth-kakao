package nextstep.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import nextstep.auth.AuthContext;

public class DecodedJwtToken {
    private final String rawToken;

    public DecodedJwtToken(String rawToken) {
        this.rawToken = rawToken;
    }

    public Long getPrincipal() {
        if (rawToken == null) {
            return null;
        }

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(AuthContext.SECRET_KEY.getBytes()))
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
