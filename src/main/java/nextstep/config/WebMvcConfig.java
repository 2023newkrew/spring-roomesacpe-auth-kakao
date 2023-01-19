package nextstep.config;

import java.util.List;
import nextstep.auth.AuthService;
import nextstep.interceptor.LoginInterceptor;
import nextstep.member.MemberService;
import nextstep.resolver.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthService authService;

    private final MemberService memberService;

    public WebMvcConfig(AuthService authService, MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor(authService, memberService))
                .addPathPatterns("/reservations/**")
                .addPathPatterns("/members/me");
    }

    @Bean
    public LoginInterceptor loginInterceptor(AuthService authService, MemberService memberService) {
        return new LoginInterceptor(authService, memberService);
    }

}
