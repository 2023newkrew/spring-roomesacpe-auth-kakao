package nextstep.auth.domain;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;

import java.util.Date;

@Getter
public class AccessToken extends Jwt {

    private static final long EXPIRATION_SECOND = 3_600_000;

    private final String accessToken;
    private String sub;
    private boolean expired;
    private boolean unsupported;
    private boolean malformed;

    private AccessToken(String accessToken) {
        this.accessToken = accessToken;
        validateToken();
        if (isValid()) {
            this.sub = extractSub();
        }
    }

    public static AccessToken from(String token) {

        return new AccessToken(token);
    }

    public static AccessToken create(String sub) {

        return new AccessToken(createToken(sub));
    }

    @Override
    public boolean isValid() {

        return !(expired | unsupported | malformed);
    }

    private static String createToken(String sub) {
        Claims claims = Jwts.claims().setSubject(sub);
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRATION_SECOND);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private void validateToken() {
        try {
            parserBuilder
                    .parseClaimsJws(this.accessToken)
            ;
        } catch (ExpiredJwtException e) {
            this.expired = true;
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            this.unsupported = true;
        } catch (MalformedJwtException | SignatureException e) {
            this.malformed = true;
        }
    }

    private String extractSub() {

        return parserBuilder
                .parseClaimsJws(this.accessToken)
                .getBody()
                .getSubject();
    }
}
