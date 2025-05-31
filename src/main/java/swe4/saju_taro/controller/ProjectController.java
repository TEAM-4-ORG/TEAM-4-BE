package swe4.saju_taro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.dto.ProjectTitle;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @PostMapping("/new")
    public Long newProject(@RequestParam Long user_id){
        // 해당 user_id에 새로운 project생성 project_id 반환
    }

    @GetMapping("/load/{projectId}")
    public Project loadProject(@PathVariable Long project_id){
        // 해당 project_id의 데이터 반환
    }

    @GetMapping("/list/{projectId}/consultations")
    public List<Consultation> listConsultations(@PathVariable Long projectId){
        // 해당 project의 Consultation 반환
    }

    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        boolean deleted = projectService.deleteProjectById(projectId);  //projectService 작성해야함

        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

}
