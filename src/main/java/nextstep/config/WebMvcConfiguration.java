package nextstep.config;

import nextstep.auth.AuthService;
import nextstep.common.AuthInterceptor;
import nextstep.common.LoginArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthService authService;

    public WebMvcConfiguration(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor(authService))
                .addPathPatterns("/**")
                .excludePathPatterns("/members") // 회원가입
                .excludePathPatterns("/login/token"); // 토큰 발급
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver(authService));
    }
}
