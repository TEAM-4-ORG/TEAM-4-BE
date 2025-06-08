package swe4.saju_taro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.Type;

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
        private String sajuData;
        private String tarotCards;
        private List<ConsultationResponseDTO> consultations;

    }

    @Getter
    @AllArgsConstructor
    public static class ProjectUserLoadDTO{
        private Integer project_id;
        private String title;
        private Type type;

        public ProjectUserLoadDTO(Project project) {
            this.project_id = project.getProjectId();
            this.title = project.getTitle();
            this.type = project.getType();
        }
    }
}
