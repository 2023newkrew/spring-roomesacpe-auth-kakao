package nextstep.configurations;

import nextstep.infrastructure.AuthenticationPrincipalArgumentResolver;
import nextstep.infrastructure.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public JwtTokenProvider jwtTokenProvider(){
        return new JwtTokenProvider();
    }
    @Bean
    public AuthenticationPrincipalArgumentResolver resolver(){
        return new AuthenticationPrincipalArgumentResolver(jwtTokenProvider());
    }

    /**
     * https://www.baeldung.com/spring-mvc-custom-data-binder#1-custom-argument-resolver
     * <p>
     * AuthenticationPrincipalArgumentResolver 등록하기
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver());
    }
}
