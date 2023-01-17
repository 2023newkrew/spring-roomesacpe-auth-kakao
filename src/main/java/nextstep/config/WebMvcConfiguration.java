package nextstep.config;

import nextstep.auth.AuthInterceptor;
import nextstep.auth.AuthPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthPrincipalArgumentResolver authPrincipalArgumentResolver;
    private final AuthInterceptor authInterceptor;

    public WebMvcConfiguration(AuthPrincipalArgumentResolver authPrincipalArgumentResolver, AuthInterceptor authInterceptor) {
        this.authPrincipalArgumentResolver = authPrincipalArgumentResolver;
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/reservations");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authPrincipalArgumentResolver);
    }
}
