package swe4.saju_taro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDTO {

    @Getter
    @AllArgsConstructor
    public static class UserDTO{
        private Integer user_id;
    }

    @Getter
    @AllArgsConstructor
    public static class UserLoadDTO{
        private Integer user_id;
        private LocalDate birth;
        private LocalTime time;
        private boolean gender;
        private List<ProjectResponseDTO.ProjectUserLoadDTO> projects;
    }

}
