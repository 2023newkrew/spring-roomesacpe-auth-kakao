package nextstep.infra.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import nextstep.domain.context.MemberDetails;
import nextstep.domain.model.template.Role;
import nextstep.infra.exception.auth.NoSuchMemberException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static nextstep.domain.model.template.Role.*;
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

    public boolean validateRole(String token, Role role) {
        Role memberRole = getPrincipal(token).getRole();
        if (role.equals(ADMIN)) {
            return memberRole.equals(ADMIN);
        }
        if (role.equals(USER)) {
            return memberRole.equals(ADMIN) || memberRole.equals(USER);
        }
        return false;
    }
}
