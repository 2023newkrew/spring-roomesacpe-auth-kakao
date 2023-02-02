package nextstep.member;

import nextstep.type.UserType;

public class ChangeUserTypeRequest {

    private final Long userId;
    private final UserType userType;
    private final String secretKey;

    public ChangeUserTypeRequest(Long userId, UserType userType, String secretKey) {
        this.userId = userId;
        this.userType = userType;
        this.secretKey = secretKey;
    }

    public Long getUserId() {
        return userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getSecretKey() {
        return secretKey;
    }
}
