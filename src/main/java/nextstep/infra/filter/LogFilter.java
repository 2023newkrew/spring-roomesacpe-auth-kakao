package nextstep.infra.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("Request", UUID.randomUUID().toString());
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        log.info("요청 발생 : {} {} ", httpRequest.getMethod(), httpRequest.getRequestURI());

        chain.doFilter(request, response);
        MDC.clear();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
