package nextstep.infrastructure.web;

import io.jsonwebtoken.*;
import nextstep.interfaces.exception.AuthorizationException;

import java.util.Date;


public class JwtTokenProvider {
    private String secretKey = "learning-test-spring";
    private long validityInMilliseconds = 3600000;

    public String createToken(String memberId, String role) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put("role", role);
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getMemberId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }
    public String getRole(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role");
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (MalformedJwtException e){
            throw new AuthorizationException("손상된 토큰입니다.");
        } catch (ExpiredJwtException e){
            throw new AuthorizationException("만료된 토큰입니다.");
        } catch (UnsupportedJwtException e){
            throw new AuthorizationException("지원하지 않는 토큰입니다.");
        } catch (SignatureException e ){
            throw new AuthorizationException("시그니처 검증에 실패한 토큰입니다.");
        }
    }
}
