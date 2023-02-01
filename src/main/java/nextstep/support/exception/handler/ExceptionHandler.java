package nextstep.support.exception.handler;

import nextstep.support.exception.DuplicateEntityException;
import nextstep.support.exception.InterceptorExcpetion;
import nextstep.support.exception.NotExistEntityException;
import nextstep.support.exception.ResolverException;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> exceptionHandler(final ResolverException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({DuplicateEntityException.class})
    public ResponseEntity<String> exceptionHandler(final DuplicateEntityException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({NotExistEntityException.class})
    public ResponseEntity<String> exceptionHandler(final NotExistEntityException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
