package nextstep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    AuthenticationPrincipalArgumentResolver resolver;
    public WebMvcConfiguration(AuthenticationPrincipalArgumentResolver resolver){
        this.resolver = resolver;
    }
    /**
     * https://www.baeldung.com/spring-mvc-custom-data-binder#1-custom-argument-resolver
     * <p>
     * AuthenticationPrincipalArgumentResolver 등록하기
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
