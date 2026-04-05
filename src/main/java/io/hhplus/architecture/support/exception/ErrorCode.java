package io.hhplus.architecture.support.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    LECTURE_ALREADY_ENROLLED(HttpStatus.BAD_REQUEST, "각 특강은 한 번만 신청할 수 있습니다."),
    LECTURE_FULL(HttpStatus.BAD_REQUEST, "신청 가능한 최대 정원이 초과되었습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
