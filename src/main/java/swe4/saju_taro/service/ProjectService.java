package swe4.saju_taro.service;

import swe4.saju_taro.dto.ProjectRequestDTO;
import swe4.saju_taro.dto.ProjectResponseDTO;

public interface ProjectService {
    ProjectResponseDTO.ProjectCreateDTO createNewProject(ProjectRequestDTO.ProjectDTO projectDTO);
}
