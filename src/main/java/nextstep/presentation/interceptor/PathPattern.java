package nextstep.presentation.interceptor;

import java.util.List;

public class PathPattern {

    private String pattern;
    private List<String> httpMethods;

    public PathPattern(String pattern, List<String> httpMethods) {
        this.pattern = pattern;
        this.httpMethods = httpMethods;
    }

    public boolean containsHttpMethod(String httpMethod) {
        return httpMethods.contains(httpMethod);
    }

    public String getPattern() {
        return pattern;
    }
}
