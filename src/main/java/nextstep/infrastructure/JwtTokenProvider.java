package nextstep.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import nextstep.auth.role.Role;
import nextstep.support.exception.NoSuchMemberException;
import nextstep.support.exception.NoSuchTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private static final String MEMBER_DETAIL = "memberDetail";
    private final ObjectMapper objectMapper;
    @Value("${auth.secretKey}")
    private String secretKey;

    @Value("${auth.validityInMilliseconds}")
    private long validityInMilliseconds;

    public String createToken(MemberDetails memberDetails) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(memberDetails.getId()));
        claims.put(MEMBER_DETAIL, memberDetails);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public MemberDetails getPrincipal(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

        if (!hasText(claims.getSubject())) {
            throw new NoSuchMemberException();
        }
        return objectMapper.convertValue(claims.get(MEMBER_DETAIL), MemberDetails.class);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

            return !claims.getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
