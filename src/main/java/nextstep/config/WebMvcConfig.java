package nextstep.config;

import nextstep.domain.auth.AuthService;
import nextstep.interceptor.AdminInterceptor;
import nextstep.interceptor.LoginInterceptor;
import nextstep.resolver.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthService authService;

    public WebMvcConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(authService))
                .addPathPatterns("/reservations/**")
                .addPathPatterns("/members/me");

        registry.addInterceptor(new AdminInterceptor(authService))
                .addPathPatterns("/admin/**");
    }
}
