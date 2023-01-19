package nextstep.config;

import nextstep.auth.AuthenticationPrincipalArgumentResolver;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.interceptor.AdminInterceptor;
import nextstep.auth.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    public WebMvcConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver(jwtTokenProvider));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor(jwtTokenProvider)).addPathPatterns("/admin/**", "/reservations/**");
        registry.addInterceptor(new AdminInterceptor(jwtTokenProvider)).addPathPatterns("/admin/**");
    }

}