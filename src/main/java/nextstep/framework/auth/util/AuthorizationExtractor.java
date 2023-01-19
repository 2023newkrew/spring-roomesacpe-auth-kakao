package nextstep.framework.auth.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import java.util.Objects;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthorizationExtractor {
    public static final String BEARER_TYPE = "Bearer";

    public static String extractTokenFromRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String header = request.getHeader(AUTHORIZATION);
        if (header != null && header.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }
}
