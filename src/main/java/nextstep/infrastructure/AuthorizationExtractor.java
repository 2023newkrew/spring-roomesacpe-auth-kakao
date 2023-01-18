package nextstep.infrastructure;

import nextstep.support.exception.NoAccessTokenException;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";
    public static final String BEARER_TYPE = "Bearer";

    private AuthorizationExtractor() {
        throw new AssertionError();
    }

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        if (!headers.hasMoreElements()) {
            throw new NoAccessTokenException("액세스 토큰이 존재하지 않습니다.");
        }
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase()
                    .startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length())
                        .trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length())
                        .trim());
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }
}

