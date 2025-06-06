package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TarotRequestDTO {
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("project_id")
    private Integer projectId;
    private List<String> cards;
    private String question;
}