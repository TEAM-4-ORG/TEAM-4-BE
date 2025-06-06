package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ProjectResponseDTO {

    @Getter
    @AllArgsConstructor
    public static class ProjectCreateDTO {
        private String title;
    }

    @Getter
    @AllArgsConstructor
    public static class ProjectLoadDTO {
        @JsonProperty("project_id")
        private Integer projectId;
        @JsonProperty("user_id")
        private Integer userId;
        private String title;
        private String type;
        private List<ConsultationResponseDTO> consultations;

    }
}
