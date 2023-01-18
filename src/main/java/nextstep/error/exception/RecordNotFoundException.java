package nextstep.error.exception;

import nextstep.error.ErrorCode;

public class RecordNotFoundException extends CustomException {
    private final ErrorCode errorCode;

    public RecordNotFoundException(ErrorCode errorCode) {
        super(errorCode, null);
        this.errorCode = errorCode;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
