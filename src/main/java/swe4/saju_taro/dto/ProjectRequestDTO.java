package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swe4.saju_taro.domain.Type;

public class ProjectRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDTO {
        @JsonProperty("user_id")
        private Integer userId;
        private Type type;
        @JsonProperty("first_question")
        private String firstQuestion;
    }
}
