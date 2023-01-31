package nextstep.auth;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthMvcConfiguration implements WebMvcConfigurer {

    private final AuthenticationPrincipalArgumentResolver resolver;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthMvcConfiguration(AuthenticationPrincipalArgumentResolver resolver, JwtTokenProvider jwtTokenProvider) {
        this.resolver = resolver;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor(jwtTokenProvider)).addPathPatterns("/admin/**");
    }
}
