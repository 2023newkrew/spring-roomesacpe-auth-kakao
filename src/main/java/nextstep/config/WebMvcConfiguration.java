package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.infrastructure.interceptor.AdminInterceptor;
import nextstep.infrastructure.interceptor.LoginArgumentResolver;
import nextstep.infrastructure.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final LoginArgumentResolver loginArgumentResolver;
    private final UserInterceptor userInterceptor;
    private final AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/**/admin/**");

        registry.addInterceptor(userInterceptor)
                .excludePathPatterns("/**/admin/**", "/login/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
