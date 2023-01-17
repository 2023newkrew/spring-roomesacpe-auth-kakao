package nextstep.auth;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * This contains static method which can convert authorization header to token.
 * <br><br>
 * 이 소스를 그대로 썼습니다(기술온보딩 제공) :
 * <a href="https://github.com/next-step/spring-learning-test/blob/auth/src/main/java/nextstep/helloworld/auth/infrastructure/AuthorizationExtractor.java">
 *     AuthorizationExtractor.java</a>
 */
public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "authorization";
    public static String BEARER_TYPE = "Bearer";
    public static final String ACCESS_TOKEN_TYPE = AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    public static String extract(HttpServletRequest request) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
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
