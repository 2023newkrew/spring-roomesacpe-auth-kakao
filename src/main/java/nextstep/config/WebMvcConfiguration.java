package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.domain.model.template.Role;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.interfaces.interceptor.LoginArgumentResolver;
import nextstep.interfaces.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider, Role.ADMIN))
                .addPathPatterns("/**/admin/**");

        registry.addInterceptor(new LoginInterceptor(jwtTokenProvider, Role.USER))
                .excludePathPatterns("/**/admin/**", "/login/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginArgumentResolver(jwtTokenProvider));

        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
