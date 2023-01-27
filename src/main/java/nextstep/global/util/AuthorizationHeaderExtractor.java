package nextstep.global.util;

import java.util.Enumeration;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.Strings;

public class AuthorizationHeaderExtractor {
    private static final String AUTHORIZATION = "Authorization";

    public static Optional<String> extract(HttpServletRequest request, TokenType type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);

        while(headers.hasMoreElements()) {
            String header = headers.nextElement();
            if(header.toLowerCase().startsWith(type.name().toLowerCase())) {
                return Optional.of(header.substring(type.name().length()).trim());
            }
        }

        return Optional.empty();
    }

    public enum TokenType {
        BASIC, BEARER, DIGEST, HOBA, MUTUAL
    }
}
