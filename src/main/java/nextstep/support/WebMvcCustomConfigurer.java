package nextstep.support;

import nextstep.support.util.JwtTokenProvider;
import nextstep.service.MemberService;
import nextstep.support.resolver.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
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
}
