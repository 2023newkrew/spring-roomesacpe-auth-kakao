package nextstep.config;

import nextstep.auth.AuthArgumentResolver;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.AuthInterceptor;
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
    public AuthArgumentResolver authArgumentResolver() {
        return new AuthArgumentResolver(jwtTokenProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login/token", "/members");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver());
    }
}
