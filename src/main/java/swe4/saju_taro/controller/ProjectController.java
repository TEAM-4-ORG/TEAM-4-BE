package swe4.saju_taro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.common.ApiResponse;
import swe4.saju_taro.common.status.SuccessStatus;
import swe4.saju_taro.dto.ProjectRequestDTO;
import swe4.saju_taro.service.ProjectService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/new")
    public ApiResponse<?> newProject(@RequestBody ProjectRequestDTO.ProjectDTO projectDTO){
        return ApiResponse.onSuccess(SuccessStatus.PROJECT_CREATED, projectService.createNewProject(projectDTO));
    }

    @GetMapping("/load/{projectId}")
    public ApiResponse<?> loadProject(@PathVariable Integer projectId){
        return ApiResponse.onSuccess(SuccessStatus._OK, projectService.loadProject(projectId));

    }

    @DeleteMapping("/delete/{projectId}")
    public ApiResponse<?> deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
        return ApiResponse.onSuccess(SuccessStatus.PROJECT_DELETED, null);
    }

}
