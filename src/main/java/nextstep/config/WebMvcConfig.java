package nextstep.config;

import java.util.List;
import nextstep.admin.AdminInterceptor;
import nextstep.admin.AdminService;
import nextstep.auth.JwtTokenProvider;
import nextstep.auth.LoginArgumentResolver;
import nextstep.auth.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final JwtTokenProvider jwtTokenProvider;
    private final AdminService adminService;

    public WebMvcConfig(JwtTokenProvider jwtTokenProvider, AdminService adminService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.adminService = adminService;
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor(jwtTokenProvider);
    }

    @Bean
    public AdminInterceptor adminInterceptor() {
        return new AdminInterceptor(jwtTokenProvider, adminService);
    }

    @Bean
    public LoginArgumentResolver loginArgumentResolver() {
        return new LoginArgumentResolver(jwtTokenProvider);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/token", "/members");
        registry.addInterceptor(adminInterceptor())
                .order(2)
                .addPathPatterns("/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginArgumentResolver());
    }
}
