package org.delivery.api.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * User의 경우 1000 번대 에러코드 사용
 */
@AllArgsConstructor
@Getter
public enum UserErrorCode {

    USER_NOT_FOUND(400, 1404, "사용자를 찾을 수 없음.")
    ;

    private final Integer httpStausCode;
    private final Integer errorCode;
    private final String description;
}

