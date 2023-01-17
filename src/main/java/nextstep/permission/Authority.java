package nextstep.permission;

public enum Authority {
    ADMIN(100),
    GUEST(0),
    USER(50);

    static final int MAX_SECURITY_LEVEL = 100;
    static final int MIN_SECURITY_LEVEL = 0;

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
