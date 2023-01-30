package nextstep.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthenticationMemberArgumentResolver authenticationMemberArgumentResolver;
    private final LoginInterceptor loginInterceptor;
<<<<<<< HEAD
    private final AdminInterceptor adminInterceptor;
=======
>>>>>>> kimtaehyun98

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/reservations/**");
<<<<<<< HEAD

        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**");
=======
>>>>>>> kimtaehyun98
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationMemberArgumentResolver);
    }
}