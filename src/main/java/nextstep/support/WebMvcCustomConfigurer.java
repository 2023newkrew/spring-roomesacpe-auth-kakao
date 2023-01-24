package nextstep.support;

import lombok.RequiredArgsConstructor;
import nextstep.admin.AdminInterceptor;
import nextstep.auth.JwtTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.MemberService;
import nextstep.support.resolver.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcCustomConfigurer implements WebMvcConfigurer {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AdminInterceptor adminInterceptor;
    private final JwtTokenExtractor jwtTokenExtractor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticationPrincipalArgumentResolver(memberService, jwtTokenProvider, jwtTokenExtractor));
    }
}
