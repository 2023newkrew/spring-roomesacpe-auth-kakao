package nextstep.config;

import nextstep.auth.AuthenticationPrincipalArgumentResolver;
import nextstep.auth.AdminRoleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final AdminRoleInterceptor adminRoleInterceptor;

    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver,
                               AdminRoleInterceptor adminRoleInterceptor) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
        this.adminRoleInterceptor = adminRoleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminRoleInterceptor).addPathPatterns("/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }
}
