package swe4.saju_taro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ProjectResponseDTO {

    @Getter
    @AllArgsConstructor
    public static class ProjectCreateDTO {
        private String title;
    }
}
