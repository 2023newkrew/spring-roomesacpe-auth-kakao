package nextstep.auth;

import static nextstep.common.exception.ExceptionMessage.ACCESSTOKEN_IS_NULL;
import static nextstep.common.exception.ExceptionMessage.INVALID_TOKEN;

import io.jsonwebtoken.*;
import java.util.HashMap;
import java.util.Map;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.common.exception.NoAccessTokenException;
import nextstep.member.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.key.secret}")
    private String secretKey;

    @Value("${jwt.key.role}")
    private String ROLE_KEY;

    @Value("${jwt.key.username}")
    private String USERNAME_KEY;

    @Value("${jwt.expire-time}")
    private Long validityInMilliseconds;

    public String createToken(TokenRequestDto tokenRequestDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(getClaimMap(tokenRequestDto))
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.get(USERNAME_KEY).toString();
    }

    public MemberRole getRole(String token) {
        Claims claims = getClaims(token);
        String roleName = claims.get(ROLE_KEY).toString();
        return MemberRole.findBy(roleName);
    }

    private Map<String, Object> getClaimMap(TokenRequestDto tokenRequestDto) {
        Map<String, Object> map = new HashMap<>();
        map.put(ROLE_KEY, tokenRequestDto.getRole());
        map.put(USERNAME_KEY, tokenRequestDto.getUsername());
        return map;
    }

    private Claims getClaims(String token){
        try {
            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException jwtException) {
            throw new JwtException(INVALID_TOKEN.getMessage());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new NoAccessTokenException(ACCESSTOKEN_IS_NULL.getMessage());
        }
    }
}
