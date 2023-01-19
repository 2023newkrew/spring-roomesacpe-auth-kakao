package nextstep.auth;

import io.jsonwebtoken.*;
import nextstep.member.Role;
import nextstep.support.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "learning-test-spring";
    private static final Long VALIDITY_IN_MILLISECONDS = 3600000L;


    public String createToken(String principal) {
        Claims claims = Jwts.claims()
                .setSubject(principal);
        claims.put("role", Role.USER);
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String createAdminToken(String principal) {
        Claims claims = Jwts.claims();
        claims.setSubject(principal);
        claims.put("role", Role.ADMIN);
        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String getPrincipal(String token) {
        validateToken(token);
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getRole(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("role")
                .toString();
    }

    public void validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token);

            if (claims.getBody()
                    .getExpiration()
                    .before(new Date())) {
                throw new UnauthorizedException("토큰이 만료되었습니다.");
            }
        } catch (ExpiredJwtException expiredJwtException) {
            throw new UnauthorizedException("토큰이 만료되었습니다.");
        } catch (JwtException jwtException) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new UnauthorizedException("액세스 토큰이 존재하지 않습니다.");
        }
    }
}
