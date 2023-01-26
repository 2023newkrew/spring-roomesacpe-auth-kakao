package nextstep.config;

import java.util.List;
import lombok.AllArgsConstructor;
import nextstep.common.AdminInterceptor;
import nextstep.common.AuthenticationPrincipalArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver;

    private final AdminInterceptor adminInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationPrincipalArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
            .addPathPatterns("/admin/**");
    }
}
