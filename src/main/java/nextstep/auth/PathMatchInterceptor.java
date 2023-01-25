package nextstep.auth;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PathMatchInterceptor implements HandlerInterceptor {
    private final HandlerInterceptor handlerInterceptor;
    private final PathContainer pathContainer;

    public PathMatchInterceptor(LoginInterceptor handlerInterceptor) {
        this.handlerInterceptor = handlerInterceptor;
        this.pathContainer = new PathContainer();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (pathContainer.isPathAndMethodNotIncluded(request.getServletPath(), request.getMethod())) {
            return true;
        }

        return handlerInterceptor.preHandle(request, response, handler);
    }

    public PathMatchInterceptor includePathPattern(String pathPattern, HttpMethod httpMethod) {
        pathContainer.addIncludePathPattern(pathPattern, httpMethod);
        return this;
    }

    public PathMatchInterceptor excludePathPattern(String pathPattern, HttpMethod httpMethod) {
        pathContainer.addExcludePathPattern(pathPattern, httpMethod);
        return this;
    }
}
