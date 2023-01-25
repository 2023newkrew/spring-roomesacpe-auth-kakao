package nextstep.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
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

    private final static String[] ADMIN_URLS = {"/themes/*", "/admin/*", "/schedules/*"};

    private final static String[] MEMBER_URLS = {"/reservations/*"};

    private final static String[] LOGIN_REQUIRED_URLS =
            Stream.concat(Arrays.stream(ADMIN_URLS), Arrays.stream(MEMBER_URLS))
                    .toArray(String[]::new);

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authenticationMemberArgumentResolver);
    }

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtAuthFilterFilterRegistration(JwtTokenProvider jwtTokenProvider,
            AuthContext authContext) {
        FilterRegistrationBean<JwtAuthFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new JwtAuthFilter(jwtTokenProvider, authContext));
        registrationBean.setOrder(0);
        registrationBean.addUrlPatterns(LOGIN_REQUIRED_URLS);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilterFilterRegistration(AuthContext authContext) {
        FilterRegistrationBean<AdminFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AdminFilter(authContext));
        registrationBean.setOrder(1);
        registrationBean.addUrlPatterns(ADMIN_URLS);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionFilterFilterRegistrationBean() {
        FilterRegistrationBean<ExceptionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ExceptionFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        return registrationBean;
    }
}
