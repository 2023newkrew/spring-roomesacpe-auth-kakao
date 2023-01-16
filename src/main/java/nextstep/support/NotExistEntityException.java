package nextstep.support;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "NotExistEntityException")
public class NotExistEntityException extends RuntimeException {

}
