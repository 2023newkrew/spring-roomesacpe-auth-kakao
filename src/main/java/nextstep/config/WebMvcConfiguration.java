package nextstep.config;

import nextstep.auth.AuthenticationPrincipalArgumentResolver;
import nextstep.auth.AdminRoleInterceptor;
import nextstep.auth.PathMatchInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final AdminRoleInterceptor adminRoleInterceptor;
    private final PathMatchInterceptor pathMatchInterceptor;

    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver,
                               AdminRoleInterceptor adminRoleInterceptor,
                               PathMatchInterceptor pathMatchInterceptor) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
        this.adminRoleInterceptor = adminRoleInterceptor;
        this.pathMatchInterceptor = pathMatchInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminRoleInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(
                pathMatchInterceptor.includePathPattern("/members/me", HttpMethod.GET)
                        .includePathPattern("/reservations", HttpMethod.POST)
                        .includePathPattern("/reservations/{\\d+}", HttpMethod.DELETE)
                )
                .addPathPatterns("/members/me", "/reservations", "/reservations/{\\d+}");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }
}
