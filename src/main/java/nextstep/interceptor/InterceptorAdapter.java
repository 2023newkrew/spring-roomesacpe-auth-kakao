package nextstep.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //지정된 컨트롤러의 동작 이전에 수행할 동작(사전 제어)
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            @Nullable ModelAndView modelAndView) throws Exception {
        //지정된 컨트롤러의 동작 이후에 처리할 동작(사후 제어)
        //스프링 mvc의 dispatcher servlet이 화면을 처리하기 전에 동작
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                 @Nullable Exception ex) throws Exception {
        //dispatcher servlet의 화면 처리가 완료된 이후 처리할 동작
    }

}
