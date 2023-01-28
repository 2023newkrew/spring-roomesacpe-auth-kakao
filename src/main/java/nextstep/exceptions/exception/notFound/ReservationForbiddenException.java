package nextstep.exceptions.exception.notFound;

import nextstep.exceptions.exception.RestAPIException;
import org.springframework.http.HttpStatus;

public class ReservationForbiddenException extends RestAPIException {

    public ReservationForbiddenException() {
        this("해당 유저의 예약이 아닙니다.");
    }

    public ReservationForbiddenException(String responseMessage) {
        super(responseMessage);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
