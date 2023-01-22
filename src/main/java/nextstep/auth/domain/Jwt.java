package nextstep.auth.domain;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

public abstract class Jwt {

    protected static final SecretKey KEY;
    protected static final JwtParser PARSER_BUILDER;

    static {
        KEY = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(JwtHolder.signatureKey));
        PARSER_BUILDER = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build();
    }

    abstract boolean isValid();

    @Component
    private static final class JwtHolder {

        private static String signatureKey;

        public JwtHolder(@Value("${jwt.signature-key}") String signatureKey) {
            JwtHolder.signatureKey = signatureKey;
        }
    }
}
