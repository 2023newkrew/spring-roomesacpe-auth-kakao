package nextstep.support;

public class AuthenticationUtil {
    private static final String TOKEN_TYPE = "Bearer";
    public static String extractToken(String authorization) {
        if(authorization != null && authorization.startsWith(TOKEN_TYPE)) {
            return authorization.substring(TOKEN_TYPE.length());
        }
        return null;
    }
}
