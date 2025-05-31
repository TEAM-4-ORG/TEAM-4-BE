package swe4.saju_taro.controller;

import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.Project;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @PostMapping("/new")
    public Long newProject(@RequestParam Long user_id){
        // 해당 user_id에 새로운 project생성 project_id 반환
    }

    @GetMapping("/change")
    public Project changeProject(@RequestParam Long project_id){
        // 해당 project_id의 데이터 반환
    }

    @PostMapping("/delete")
    public boolean deleteProject(@RequestParam Long project_id){
        //해당 project 삭제
    }
}
