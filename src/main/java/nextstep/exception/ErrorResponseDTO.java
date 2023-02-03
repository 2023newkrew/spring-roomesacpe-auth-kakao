package nextstep.exception;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseDTO {

    private final int statusCode;

    private final String message;

    public static ErrorResponseDTO from(CustomException e) {
        return ErrorResponseDTO.builder()
                .statusCode(e.getStatus().value())
                .message(e.getMessage())
                .build();
    }
}
