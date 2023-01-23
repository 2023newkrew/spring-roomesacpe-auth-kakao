package nextstep.config;

import nextstep.auth.AuthenticationInterceptor;
import nextstep.auth.AuthenticationPrincipalArgumentResolver;
import nextstep.auth.AuthorityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final AuthorityInterceptor authorityInterceptor;
    private final AuthenticationInterceptor authenticationInterceptor;

    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver, AuthorityInterceptor authorityInterceptor, AuthenticationInterceptor authenticationInterceptor) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
        this.authorityInterceptor = authorityInterceptor;
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/members/me", "/reservations/**", "/admin/**");
        registry.addInterceptor(authorityInterceptor)
                .addPathPatterns("/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }
}
