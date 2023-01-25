package nextstep.config;

import nextstep.auth.AuthenticationPrincipalArgumentResolver;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final LoginInterceptor loginInterceptor;

    public WebMvcConfiguration(JwtTokenProvider jwtTokenProvider, LoginInterceptor loginInterceptor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver(jwtTokenProvider));
    }
}
