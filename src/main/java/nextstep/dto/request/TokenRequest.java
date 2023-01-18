package nextstep.dto.request;

public class TokenRequest {
    private long memberId;
    private String password;

    public TokenRequest() {}

    public TokenRequest(long memberId, String password) {
        this.memberId = memberId;
        this.password = password;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getPassword() {
        return password;
    }
}
