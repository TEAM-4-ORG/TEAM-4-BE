package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TarotSaveRequestDTO {
    @JsonProperty("project_id")
    private Integer projectId;
    private List<String> cards;
}
