package nextstep.config;

import nextstep.ui.AuthenticationPrincipalArgumentResolver;
import nextstep.ui.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    private AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private LoginInterceptor loginInterceptor;

    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver, LoginInterceptor loginInterceptor) {
        this.authenticationPrincipalArgumentResolver = authenticationPrincipalArgumentResolver;
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/member/me", "/reservations");
                // 3단계 진행에서 관리자 설정 시 테스트 및 인터셉터 대상 폴더 수정 예정
                // .addPathPatterns("/member/me", "/reservations", "/themes", "/schedules");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }
}
