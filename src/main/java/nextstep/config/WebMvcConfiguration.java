package nextstep.config;

import nextstep.auth.principal.MemberAuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final MemberAuthenticationPrincipalArgumentResolver memberAuthenticationPrincipalArgumentResolver;

    public WebMvcConfiguration(MemberAuthenticationPrincipalArgumentResolver memberAuthenticationPrincipalArgumentResolver) {
        this.memberAuthenticationPrincipalArgumentResolver = memberAuthenticationPrincipalArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberAuthenticationPrincipalArgumentResolver);
    }
}