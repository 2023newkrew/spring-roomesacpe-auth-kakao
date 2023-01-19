package nextstep.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.auth.AuthService;
import nextstep.interceptor.AdminInterceptor;
import nextstep.interceptor.LoginInterceptor;
import nextstep.member.MemberService;
import nextstep.resolver.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;

    private final MemberService memberService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor(authService, memberService))
                .addPathPatterns("/reservations/**")
                .addPathPatterns("/members/me");
        registry.addInterceptor(adminInterceptor(authService, memberService))
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");
    }

    @Bean
    public LoginInterceptor loginInterceptor(AuthService authService, MemberService memberService) {
        return new LoginInterceptor(authService, memberService);
    }

    @Bean
    public AdminInterceptor adminInterceptor(AuthService authService, MemberService memberService) {
        return new AdminInterceptor(authService, memberService);
    }
}
