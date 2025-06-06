package swe4.saju_taro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class ProjectResponseDTO {

    @Getter
    @AllArgsConstructor
    public static class ProjectCreateDTO {
        private Integer projectId;
        private String title;
    }

    @Getter
    @AllArgsConstructor
    public static class ProjectLoadDTO {
        private Integer projectId;
        private Integer userId;
        private String title;
        private String type;
        private List<ConsultationResponseDTO> consultations;

    }
}
