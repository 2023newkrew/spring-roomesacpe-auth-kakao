package nextstep.error.exception;

import nextstep.error.ErrorCode;

public class FailedRecordSaveException extends CustomException {
    private static final ErrorCode errorCode = ErrorCode.DATABASE_SAVE_ERROR;

    public FailedRecordSaveException() {
        super(ErrorCode.DATABASE_SAVE_ERROR, null);
    }

    @Override
    public ErrorCode getErrorCode() {
        return FailedRecordSaveException.errorCode;
    }
}
