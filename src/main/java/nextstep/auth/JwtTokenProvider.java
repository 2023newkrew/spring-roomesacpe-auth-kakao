package nextstep.auth;

import static nextstep.common.exception.ExceptionMessage.ACCESSTOKEN_IS_NULL;
import static nextstep.common.exception.ExceptionMessage.INVALID_TOKEN;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import nextstep.auth.dto.TokenRequestDto;
import nextstep.common.exception.NoAccessTokenException;
import nextstep.member.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${jwt.key.secret}")
    private String secretKey;

    @Value("${jwt.key.role}")
    private String roleKey;

    @Value("${jwt.key.username}")
    private String usernameKey;

    @Value("${jwt.expire-time}")
    private Long validityInMilliseconds;

    public String createToken(TokenRequestDto tokenRequestDto) {
        Long validityInSeconds = validityInMilliseconds / 1000;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validity = now.plusSeconds(validityInSeconds);

        return Jwts.builder()
            .setClaims(getClaimMap(tokenRequestDto))
            .setIssuedAt(Timestamp.valueOf(now))
            .setExpiration(Timestamp.valueOf(validity))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String getUsername(final String token) {
        Claims claims = getClaims(token);
        return claims.get(usernameKey).toString();
    }

    public MemberRole getRole(final String token) {
        Claims claims = getClaims(token);
        String roleName = claims.get(roleKey).toString();
        return MemberRole.valueOf(roleName);
    }

    private Map<String, Object> getClaimMap(TokenRequestDto tokenRequestDto) {
        Map<String, Object> map = new HashMap<>();
        map.put(roleKey, tokenRequestDto.getRole());
        map.put(usernameKey, tokenRequestDto.getUsername());
        return map;
    }

    private Claims getClaims(final String token) {
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
