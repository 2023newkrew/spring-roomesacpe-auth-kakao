package nextstep.auth;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthMvcConfiguration implements WebMvcConfigurer {

    private final AuthenticationPrincipalArgumentResolver resolver;
    private final AdminInterceptor interceptor;

    public AuthMvcConfiguration(AuthenticationPrincipalArgumentResolver resolver, AdminInterceptor interceptor) {
        this.resolver = resolver;
        this.interceptor = interceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/admin/**");
    }
}
