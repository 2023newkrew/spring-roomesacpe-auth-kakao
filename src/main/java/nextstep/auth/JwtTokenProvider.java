package nextstep.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nextstep.config.AppConfig;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static nextstep.auth.Interceptor.LoginInterceptor.bearer;
import static nextstep.support.Messages.*;

@Component
public class JwtTokenProvider {
    public String createToken(String principal) {
        Claims claims = Jwts.claims().setSubject(principal);
        Date now = new Date();
        Date validity = new Date(now.getTime() + Long.parseLong(AppConfig.getValidTokenTime()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, AppConfig.getSecretKey())
                .compact();
    }

    public String getPrincipal(String token) {
        String bearerRemoveToken = token.substring(bearer.length());
        if (validateToken(bearerRemoveToken)) {
            return Jwts.parser().setSigningKey(AppConfig.getSecretKey()).parseClaimsJws(bearerRemoveToken).getBody().getSubject();
        }
        throw new AuthenticationServiceException(INVALID_TOKEN.getMessage());
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(AppConfig.getSecretKey()).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new AuthenticationServiceException(JWT_Exception.getMessage() + e.getMessage());
        }
    }
}
