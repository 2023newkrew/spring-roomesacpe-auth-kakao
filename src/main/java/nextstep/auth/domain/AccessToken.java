package nextstep.auth.domain;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class AccessToken extends Jwt {

    private static final long EXPIRATION_SECOND = 3_600_000;

    private final String accessToken;
    private String sub;
    private Role role;
    private boolean expired;
    private boolean unsupported;
    private boolean malformed;

    private AccessToken(String accessToken) {
        this.accessToken = accessToken;
        validateToken();
        if (isValid()) {
            this.sub = extractSub();
            this.role = extractRole();
        }
    }

    public static AccessToken from(String token) {

        return new AccessToken(token);
    }

    public static AccessToken create(String sub, String role) {

        return new AccessToken(createToken(sub, role));
    }

    private static String createToken(String sub, String role) {
        Claims claims = Jwts.claims(Map.of("role", role))
                .setSubject(sub);
        Date now = new Date();
        Date validity = new Date(now.getTime() + EXPIRATION_SECOND);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact()
                ;
    }

    @Override
    public boolean isValid() {

        return !(expired | unsupported | malformed);
    }

    private void validateToken() {
        try {
            PARSER_BUILDER
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

        return PARSER_BUILDER
                .parseClaimsJws(this.accessToken)
                .getBody()
                .getSubject();
    }

    private Role extractRole() {

        return Role.of(PARSER_BUILDER
                .parseClaimsJws(this.accessToken)
                .getBody()
                .get("role")
                .toString());
    }
}
