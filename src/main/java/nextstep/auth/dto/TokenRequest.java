package nextstep.auth.dto;


public class TokenRequest {
    private String username;
    private String password;

    /* RestAssured에서 사용 */
    @SuppressWarnings("unused")
    public TokenRequest() {
    }

    public TokenRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean validate() {
        return username != null && password != null;
    }
}