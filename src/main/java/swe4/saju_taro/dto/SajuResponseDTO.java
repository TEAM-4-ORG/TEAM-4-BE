package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SajuResponseDTO {
    @JsonProperty("consultation_id")
    private Integer consultationId;
    private String question;
    private String result;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
