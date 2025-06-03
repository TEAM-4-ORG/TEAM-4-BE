package swe4.saju_taro.dto;

public class CommonResponse {
    private boolean isSuccess;
    private String code;
    private String message;

    public CommonResponse(boolean isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
