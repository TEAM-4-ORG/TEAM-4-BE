package swe4.saju_taro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swe4.saju_taro.domain.Type;

public class ProjectRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProjectDTO {
        private Integer userId;
        private Type type;
        private String firstQuestion;
    }
}
