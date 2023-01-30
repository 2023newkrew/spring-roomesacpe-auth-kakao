package nextstep.support.interceptor;

import nextstep.support.exception.InterceptorExcpetion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class InterceptorExceptionHandler {
    @ExceptionHandler({InterceptorExcpetion.class})
    public ResponseEntity<String> exceptionHandler(HttpServletRequest request, final InterceptorExcpetion e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }
}
