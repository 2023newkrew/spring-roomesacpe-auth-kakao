package nextstep.permission;

/**
 * Authority represents how many controls does the account has.
 * <br>
 * Higher the security level is, more control the account has.
 */
// Developer comment :
public enum Authority {
    ADMIN(Authority.ADMIN_LEVEL),
    GUEST(Authority.GUEST_LEVEL),
    USER(Authority.USER_LEVEL);

    public static final int ADMIN_LEVEL = 100;
    public static final int USER_LEVEL = 50;
    public static final int GUEST_LEVEL = 0;
    public static final int MAX_SECURITY_LEVEL = 100;
    public static final int MIN_SECURITY_LEVEL = 0;

    Authority(int securityLevel){
        if (securityLevel > MAX_SECURITY_LEVEL){
            securityLevel = MAX_SECURITY_LEVEL;
        }
        if (securityLevel < MIN_SECURITY_LEVEL){
            securityLevel = MIN_SECURITY_LEVEL;
        }
        this.securityLevel = securityLevel;
    }
    private final int securityLevel;

    public int getSecurityLevel(){
        return securityLevel;
    }
}
