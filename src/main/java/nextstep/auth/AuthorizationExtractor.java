package nextstep.auth;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {
    public static final String AUTHORIZATION = "Authorization";
    public static String BEARER_TYPE = "Bearer";

    public static String extract(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header != null && header.toLowerCase().startsWith(BEARER_TYPE.toLowerCase())) {
            return header.substring(BEARER_TYPE.length()).trim();
        }
        return null;
    }
}
