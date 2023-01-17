package nextstep.support;

import nextstep.auth.MemberIdArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    //private final LoginInterceptor loginInterceptor;
    //private final RestLoginInterceptor restLoginInterceptor;
    private final MemberIdArgumentResolver memberIdArgumentResolver;

    public WebMvcConfig(MemberIdArgumentResolver memberIdArgumentResolver) {
        this.memberIdArgumentResolver = memberIdArgumentResolver;
    }

    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(loginInterceptor)
//                .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/api/**");
//
//        registry.addInterceptor(restLoginInterceptor)
//                .addPathPatterns("/api/**");
//    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(memberIdArgumentResolver);
    }
}