package swe4.saju_taro.common;

import lombok.Getter;
import swe4.saju_taro.common.code.BaseErrorCode;
import swe4.saju_taro.common.code.ErrorReasonDTO;

@Getter
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;
    public GeneralException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}