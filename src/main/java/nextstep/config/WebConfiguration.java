package nextstep.config;

import lombok.AllArgsConstructor;
import nextstep.admin.AdminInterceptor;
import nextstep.auth.JwtTokenProvider;
import nextstep.support.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {
    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public AdminInterceptor adminInterceptor(){
        return new AdminInterceptor(jwtTokenProvider);
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor()).addPathPatterns("/admin/**");
    }
}
