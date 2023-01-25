package nextstep.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import nextstep.exception.CustomException;
import nextstep.exception.ErrorCode;

public class AuthorizationExtractor {

    public static final String BEARER_TYPE = "Bearer";

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

    public static String getTokenFromHeader(final String authHeader) {
        if (authHeader == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
        String[] authInfo = authHeader.split(" ");

        if (authInfo.length != 2) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        return authInfo[1];
    }
}
