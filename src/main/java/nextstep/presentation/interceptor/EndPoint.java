package nextstep.presentation.interceptor;

public class EndPoint {

    private String httpMethod;
    private String requestUrl;

    public EndPoint(String httpMethod, String requestUrl) {
        this.httpMethod = httpMethod;
        this.requestUrl = requestUrl;
    }

    public boolean isSame(String httpMethod, String requestUrl) {
        return this.httpMethod.equals(httpMethod) && this.requestUrl.equals(requestUrl);
    }

}
