package nextstep.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import nextstep.member.Member;
import nextstep.member.dto.LoginMember;
import org.springframework.beans.factory.annotation.Value;

public class JwtTokenProvider {

    @Value("${secret-key}")
    private String secretKey = "sample";

    @Value("${validity-in-milli-seconds}")
    private long validityInMilliseconds = 3600000;

    public String createToken(Member member) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", member.getUsername());
        parameters.put("name", member.getName());
        parameters.put("id", member.getId());
        Claims claims = Jwts.claims(parameters);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public LoginMember getPrincipal(String token) {
        Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new LoginMember(body.get("id", Long.class), body.get("username", String.class),
                body.get("name", String.class));
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
