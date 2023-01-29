package nextstep.admin;

import lombok.RequiredArgsConstructor;
import nextstep.auth.JwtTokenExtractor;
import nextstep.auth.JwtTokenProvider;
import nextstep.member.Role;
import nextstep.support.exception.RoomEscapeExceptionCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {
    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> tokenOptional = jwtTokenExtractor.extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (tokenOptional.isEmpty()) {
            setAbortContext(response, HttpStatus.UNAUTHORIZED, RoomEscapeExceptionCode.INVALID_TOKEN.getErrorMessage());
            return false;
        }

        String token = tokenOptional.get();
        if (!isValidToken(token)) {
            setAbortContext(response, HttpStatus.UNAUTHORIZED, RoomEscapeExceptionCode.INVALID_TOKEN.getErrorMessage());
            return false;
        }

        if (!isAdmin(token)) {
            setAbortContext(response, HttpStatus.FORBIDDEN, RoomEscapeExceptionCode.FORBIDDEN_ACCESS.getErrorMessage());
            return false;
        }

        return true;
    }

    private boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    private boolean isAdmin(String token) {
        return jwtTokenProvider.getRole(token).map(s -> s.equals(Role.ADMIN.name())).orElse(false);
    }

    private void setAbortContext(HttpServletResponse response, HttpStatus httpStatus, String errorMessage) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(errorMessage);
        response.setStatus(httpStatus.value());
    }
}
