package nextstep.auth;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;

public class PathContainer {
    private final PathMatcher pathMatcher;
    private final List<RequestPathAndMethod> includePathPattern;
    private final List<RequestPathAndMethod> excludePathPattern;

    public PathContainer() {
        this.pathMatcher = new AntPathMatcher();
        this.includePathPattern = new ArrayList<>();
        this.excludePathPattern = new ArrayList<>();
    }

    public boolean isPathAndMethodNotIncluded(String targetPath, String pathMethod) {
        boolean isExcludeMatch = excludePathPattern.stream()
                .anyMatch(requestPathAndMethod -> isRequestPathMatch(targetPath, pathMethod, requestPathAndMethod));

        boolean isIncludeMatch = includePathPattern.stream()
                .anyMatch(requestPathAndMethod -> isRequestPathMatch(targetPath, pathMethod, requestPathAndMethod));

        return isExcludeMatch || !isIncludeMatch;
    }

    private boolean isRequestPathMatch(String targetPath, String pathMethod, RequestPathAndMethod requestPathAndMethod) {
        return pathMatcher.match(requestPathAndMethod.getPath(), targetPath) &&
                requestPathAndMethod.matchesMethod(pathMethod);
    }

    public void addIncludePathPattern(String targetPath, HttpMethod httpMethod) {
        this.includePathPattern.add(new RequestPathAndMethod(targetPath, httpMethod));
    }

    public void addExcludePathPattern(String targetPath, HttpMethod httpMethod) {
        this.excludePathPattern.add(new RequestPathAndMethod(targetPath, httpMethod));
    }
}
