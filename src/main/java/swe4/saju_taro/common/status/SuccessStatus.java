package swe4.saju_taro.common.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import swe4.saju_taro.common.code.BaseCode;
import swe4.saju_taro.common.code.ReasonDTO;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "요청에 성공했습니다."),

    // 프로젝트 관련 응답
    PROJECT_CREATED(HttpStatus.OK, "PROJECT200", "프로젝트 추가에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
