package nextstep.presentation.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PathPatternInterceptor implements HandlerInterceptor {

    private final HandlerInterceptor interceptor;
    private final PathMatcher pathMatcher;
    private final List<PathPattern> includeList;
    private final List<PathPattern> excludeList;

    public static PathPatternInterceptor from(HandlerInterceptor interceptor) {
        return new PathPatternInterceptor(interceptor);
    }

    private PathPatternInterceptor(HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
        this.pathMatcher = new AntPathMatcher();
        this.includeList = new ArrayList<>();
        this.excludeList = new ArrayList<>();
    }

    public PathPatternInterceptor addCustomPathPattern(String pattern) {
        List<String> httpMethods = Arrays.stream(HttpMethod.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        includeList.add(new PathPattern(pattern, httpMethods));
        return this;
    }

    public PathPatternInterceptor addCustomPathPattern(String pattern, String httpMethod) {
        includeList.add(new PathPattern(pattern, List.of(httpMethod)));
        return this;
    }

    public PathPatternInterceptor excludeCustomPathPattern(String pattern) {
        List<String> httpMethods = Arrays.stream(HttpMethod.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        excludeList.add(new PathPattern(pattern, httpMethods));
        return this;
    }

    public PathPatternInterceptor excludeCustomPathPattern(String pattern, String httpMethod) {
        excludeList.add(new PathPattern(pattern, List.of(httpMethod)));
        return this;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isExcludeList(request.getRequestURI(), request.getMethod())) {
            return true;
        }

        return interceptor.preHandle(request, response, handler);
    }

    private boolean isExcludeList(String requestUrl, String httpMethod) {
        return excludeList.stream()
                .anyMatch(pathPattern -> pathMatcher.match(pathPattern.getPattern(), requestUrl) && pathPattern.containsHttpMethod(httpMethod));
    }
}
