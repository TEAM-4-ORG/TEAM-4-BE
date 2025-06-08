package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SajuSaveRequestDTO {
    @JsonProperty("user_id")
    private Integer userId;
    private Map<String, Object> sajuData;
}
