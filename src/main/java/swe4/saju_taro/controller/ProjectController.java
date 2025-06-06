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
//
//    @GetMapping("/load/{projectId}")
//    public Project loadProject(@PathVariable Long project_id){
//        // 해당 project_id의 데이터 반환
//    }
//
//    @GetMapping("/list/{projectId}/consultations")
//    public List<Consultation> listConsultations(@PathVariable Long projectId){
//        // 해당 project의 Consultation 반환
//    }
//
//    @DeleteMapping("/delete/{projectId}")
//    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
//        boolean deleted = projectService.deleteProjectById(projectId);  //projectService 작성해야함
//
//        if (deleted) {
//            return ResponseEntity.noContent().build(); // 204 No Content
//        } else {
//            return ResponseEntity.notFound().build(); // 404 Not Found
//        }
//    }

}
