package nextstep.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthenticationMemberArgumentResolver authenticationMemberArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationMemberArgumentResolver);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> filterRegistration(JwtTokenProvider jwtTokenProvider) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(jwtTokenProvider));
        registrationBean.addUrlPatterns("/reservations/*");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionFilterFilterRegistrationBean() {
        FilterRegistrationBean<ExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ExceptionFilter());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
