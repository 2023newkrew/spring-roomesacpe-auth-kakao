package nextstep.support;

import nextstep.auth.JwtTokenProvider;
import nextstep.auth.LoginInterceptor;
import nextstep.auth.MemberIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {
    private final MemberIdArgumentResolver memberIdArgumentResolver;
    private final JwtTokenProvider jwtTokenProvider;

    public WebMvcConfig(MemberIdArgumentResolver memberIdArgumentResolver, JwtTokenProvider jwtTokenProvider) {
        this.memberIdArgumentResolver = memberIdArgumentResolver;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider))
                .addPathPatterns("/admin/**", "/members/me", "/reservations/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(memberIdArgumentResolver);
    }
}