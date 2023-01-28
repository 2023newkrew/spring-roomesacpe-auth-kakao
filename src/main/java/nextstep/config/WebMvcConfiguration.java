package nextstep.config;

import nextstep.support.AdminVerificationInterceptor;
import nextstep.support.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final LoginMemberArgumentResolver loginMemberArgumentResolver;
    private final AdminVerificationInterceptor adminVerificationInterceptor;

    public WebMvcConfiguration(LoginMemberArgumentResolver loginMemberArgumentResolver, AdminVerificationInterceptor adminVerificationInterceptor) {
        this.loginMemberArgumentResolver = loginMemberArgumentResolver;
        this.adminVerificationInterceptor = adminVerificationInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginMemberArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminVerificationInterceptor).addPathPatterns("/admin/**");
    }
}
