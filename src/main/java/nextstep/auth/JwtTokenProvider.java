package nextstep.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static nextstep.auth.Interceptor.LoginInterceptor.bearer;
import static nextstep.support.Messages.*;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.validateMilliSeconds}")
    private String validityInMilliseconds;

    public String createToken(String principal) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + Long.parseLong(validityInMilliseconds));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPrincipal(String token) {
        String bearerRemoveToken = token.substring(bearer.length());
        if (validateToken(bearerRemoveToken)) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(bearerRemoveToken).getBody().getSubject();
        }
        throw new AuthenticationServiceException(INVALID_TOKEN.getMessage());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationServiceException(JWT_Exception.getMessage() + e.getMessage());
        }
    }
}
