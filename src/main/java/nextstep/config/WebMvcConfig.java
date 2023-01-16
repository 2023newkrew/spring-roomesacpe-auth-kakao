package nextstep.config;

import nextstep.auth.utils.JwtTokenProvider;
import nextstep.auth.presentation.argumentresolver.AuthenticationPrincipalArgumentResolver;
import nextstep.auth.presentation.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public WebMvcConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor(jwtTokenProvider);
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver authArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/token", "/members");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver());
    }
}
