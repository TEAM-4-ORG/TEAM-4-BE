package swe4.saju_taro.common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "isSuccess", "code", "message", "result" })
public class CommonResponse<T> {

    @JsonProperty("isSuccess")
    private boolean isSuccess;

    @Getter
    @JsonProperty("code")
    private String code;

    @Getter
    @JsonProperty("message")
    private String message;

    @Getter
    @JsonProperty("result")
    private T result;

    @JsonIgnore
    public boolean getSuccess() {
        return isSuccess;
    }

    public CommonResponse(boolean isSuccess, String code, String message, T result) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public CommonResponse(boolean isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

}
