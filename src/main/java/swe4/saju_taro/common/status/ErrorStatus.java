package swe4.saju_taro.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import swe4.saju_taro.common.code.BaseErrorCode;
import swe4.saju_taro.common.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public enum
ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    MISSING_REQUIRED_VALUE(HttpStatus.BAD_REQUEST, "COMMON400", "입력되지 않은 필수값이 있습니다."),

    // 사용자 관련 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "사용자를 찾을 수 없습니다."),
    USER_WRONG_BIRTH(HttpStatus.BAD_REQUEST, "USER400", "유효하지 않은 생년월일 형식입니다."),
    USER_WRONG_TIME(HttpStatus.BAD_REQUEST, "USER400", "유효하지 않은 시간 형식입니다."),

    // 프로젝트 관련 응답
    PROJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "PROJECT404", "해당 프로젝트를 찾을 수 없습니다."),

    // AI 서버 관련 응답
    AI_RESPONSE_FAILED(HttpStatus.BAD_REQUEST, "AI400", "AI 인터렉션 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
