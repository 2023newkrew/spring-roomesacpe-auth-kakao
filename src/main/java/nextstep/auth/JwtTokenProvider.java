package nextstep.auth;

import io.jsonwebtoken.*;
import nextstep.auth.role.Role;
import nextstep.exception.business.BusinessException;
import nextstep.exception.business.BusinessErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class
JwtTokenProvider {

    public static final String ACCESS_TOKEN = "accessJWT";

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;
    @Value("${security.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public String createToken(String principal, List<Role> roles) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPrincipal(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRoles(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("roles", List.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new BusinessException(BusinessErrorCode.TOKEN_NOT_EXIST);
        }
        Optional<Cookie> accessToken = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN))
                .findFirst();
        if (accessToken.isEmpty()) {
            throw new BusinessException(BusinessErrorCode.TOKEN_NOT_EXIST);
        }
        return accessToken.get().getValue();
    }

    public long getValidityInMilliseconds() {
        return validityInMilliseconds;
    }
}
