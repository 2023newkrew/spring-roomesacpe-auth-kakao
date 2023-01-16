package nextstep.config;

import nextstep.auth.AuthPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthPrincipalArgumentResolver authPrincipalArgumentResolver;

    public WebMvcConfiguration(AuthPrincipalArgumentResolver authPrincipalArgumentResolver) {
        this.authPrincipalArgumentResolver = authPrincipalArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authPrincipalArgumentResolver);
    }
}
