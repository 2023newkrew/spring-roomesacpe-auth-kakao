package nextstep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvcConfiguration
 *
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    AuthenticationPrincipalArgumentResolver resolver;
    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver resolver){
        this.resolver = resolver;
    }

    /**
     * @param resolvers injected by Spring, add custom resolver to this.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
