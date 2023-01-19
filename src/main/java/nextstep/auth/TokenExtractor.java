package nextstep.auth;

import nextstep.error.ErrorCode;
import nextstep.error.exception.AuthenticationException;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

public class TokenExtractor {
    private TokenExtractor() {
    }

    public static String extract(HttpServletRequest request) {
        var bearerToken = request.getHeader("authorization");

        return getAccessToken(bearerToken);
    }

    public static String extract(NativeWebRequest request) {
        var bearerToken = request.getHeader("authorization");

        return getAccessToken(bearerToken);
    }

    private static String getAccessToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new AuthenticationException(ErrorCode.NO_TOKEN);
        }

        return bearerToken.substring(7);
    }

}
