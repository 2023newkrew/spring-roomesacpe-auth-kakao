package nextstep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvcConfiguration configures what to do in specific situation.
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final AuthenticationPrincipalArgumentResolver resolver;
    private final AdminInterceptor adminInterceptor;
    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver resolver,
                               AdminInterceptor adminInterceptor){
        this.resolver = resolver;
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
    }

    /**
     * @param resolvers injected by Spring, add custom resolver to this.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
