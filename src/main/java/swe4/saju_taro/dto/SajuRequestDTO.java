package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SajuRequestDTO {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("project_id")
    private Integer projectId;
    private String question;
    private Map<String, Object> sajuData;
}
