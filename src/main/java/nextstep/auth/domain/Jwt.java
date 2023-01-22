package nextstep.auth.domain;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public abstract class Jwt {

    protected static final String secretKey = "lXH810veBaPOZAh5WN4cuo9BFRolED9ELuOuUa10lQCBcn0ckL";
    protected static final SecretKey key;
    protected static final JwtParser parserBuilder;

    static {
        key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
        parserBuilder = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
    }

    abstract boolean isValid();
}
