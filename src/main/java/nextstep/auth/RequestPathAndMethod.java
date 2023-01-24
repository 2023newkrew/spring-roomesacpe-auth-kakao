package nextstep.auth;

import org.springframework.http.HttpMethod;

import java.util.Objects;

public class RequestPathAndMethod {
    private final String path;
    private final HttpMethod method;

    public RequestPathAndMethod(String path, HttpMethod method) {
        this.path = path;
        this.method = method;
    }

    public boolean matchesMethod(String method) {
        return this.method.matches(method);
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestPathAndMethod that = (RequestPathAndMethod) o;
        return Objects.equals(path, that.path) && method == that.method;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method);
    }
}
