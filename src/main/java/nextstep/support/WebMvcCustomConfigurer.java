package nextstep.support;

import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberService;
import nextstep.support.interceptor.AdminHandlerInterceptor;
import nextstep.support.resolver.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcCustomConfigurer implements WebMvcConfigurer {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public WebMvcCustomConfigurer(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver(memberService, jwtTokenProvider));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminHandlerInterceptor(memberService, jwtTokenProvider)).addPathPatterns("/admin/**");
    }
}
