package nextstep.config;

import nextstep.auth.Interceptor.AdminInterceptor;
import nextstep.auth.Interceptor.LoginInterceptor;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.principal.MemberAuthenticationPrincipalArgumentResolver;
import nextstep.member.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final MemberAuthenticationPrincipalArgumentResolver memberAuthenticationPrincipalArgumentResolver;
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public WebMvcConfiguration(MemberAuthenticationPrincipalArgumentResolver memberAuthenticationPrincipalArgumentResolver, MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberAuthenticationPrincipalArgumentResolver = memberAuthenticationPrincipalArgumentResolver;
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/reservations/**")
                .addPathPatterns("/schedules/**")
                .addPathPatterns("/themes/**")
                .addPathPatterns("/admin/**");
        registry.addInterceptor(new AdminInterceptor(memberService, jwtTokenProvider))
                .addPathPatterns("/admin/**");
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberAuthenticationPrincipalArgumentResolver);
    }
}