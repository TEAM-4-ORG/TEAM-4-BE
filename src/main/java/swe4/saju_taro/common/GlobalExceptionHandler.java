package swe4.saju_taro.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import swe4.saju_taro.common.code.ErrorReasonDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(GeneralException ex) {
        ErrorReasonDTO errorResponse = ex.getErrorReasonHttpStatus();
        ApiResponse<Object> response = ApiResponse.onFailure(
                errorResponse.getCode(),
                errorResponse.getMessage(),
                errorResponse.getHttpStatus().name()
        );
        return ResponseEntity
                .status(errorResponse.getHttpStatus())
                .body(response);
    }
}
