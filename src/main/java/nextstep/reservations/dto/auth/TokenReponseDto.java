package nextstep.reservations.dto.auth;

public class TokenReponseDto {
    private String accessToken;

    /* RestAssured에서 reflection에 사용 */
    @SuppressWarnings("unused")
    public TokenReponseDto() {
    }

    public TokenReponseDto(final String accessToken) {
        this.accessToken = accessToken;
    }

    /* RestAssured에서 reflection에 사용 */
    @SuppressWarnings("unused")
    public String getAccessToken() {
        return accessToken;
    }
}
