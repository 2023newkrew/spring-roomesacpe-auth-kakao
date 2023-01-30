package nextstep.support.exception.handler;

import nextstep.support.exception.InterceptorExcpetion;
import nextstep.support.exception.ResolverException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler({InterceptorExcpetion.class})
    public ResponseEntity<String> exceptionHandler(HttpServletRequest request, final InterceptorExcpetion e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ResolverException.class})
    public ResponseEntity<String> exceptionHandler(HttpServletRequest request, final ResolverException e) {
        System.out.println("\nenter to exception handler\n");
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }
}
