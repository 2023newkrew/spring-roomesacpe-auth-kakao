package nextstep.infra.jwt;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class AuthorizationExtractor {
    private static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";

    public String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if (isBearerType(value)) {
                return value.substring(BEARER_TYPE.length()).trim();
            }
        }
        return Strings.EMPTY;
    }

    private boolean isBearerType(String value) {
        return value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase());
    }
}