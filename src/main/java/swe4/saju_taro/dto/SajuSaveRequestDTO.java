package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SajuSaveRequestDTO {
    @JsonProperty("user_id")
    private Integer userId;
    private Map<String, Object> sajuData;
}
