package nextstep.exception;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ExceptionController {
    @GetMapping("/api/error")
    public void error(HttpServletRequest request) throws AuthenticationException {
        String exception = (String) request.getAttribute("exception");

        if ("AuthenticationException".equals(exception)) {
            throw new AuthenticationException();
        }
        if ("UnAuthorizationException".equals(exception)) {
            throw new UnAuthorizationException();
        }
    }
}
