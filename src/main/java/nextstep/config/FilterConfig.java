package nextstep.config;

import lombok.RequiredArgsConstructor;
import nextstep.infra.jwt.JwtTokenProvider;
import nextstep.interfaces.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Deprecated
@Configuration
@RequiredArgsConstructor
public class FilterConfig {
//    private final JwtTokenProvider jwtTokenProvider;
//    @Bean
//    public FilterRegistrationBean adminFilter() {
//        FilterRegistrationBean<Filter> registrationAdminFilter = new FilterRegistrationBean<>();
//        registrationAdminFilter.setFilter(new AdminFilter(jwtTokenProvider));
//
//        return registrationAdminFilter;
//    }
}
