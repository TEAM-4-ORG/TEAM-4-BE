package swe4.saju_taro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.ProjectTitle;
import swe4.saju_taro.dto.UserRequest;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info/{userId}")
    public User getUserInfo(@PathVariable Long user_id){
        // DB에서 User 가져옴
    }

    @PostMapping("/new")
    public Long newUser(@RequestBody UserRequest request){
        // UserRequest DB 저장후 userid 반환
    }

    @PutMapping("/change/{userId}")
    public Long changeUserInfo(@PathVariable Long userId, @RequestBody UserRequest request){
        // userid, UserRequest(사주 정보) 를 받아 DB 변경후 user_id 반환
    }

    @GetMapping("/list/{userId}/projects")
    public List<ProjectTitle> listProject(@PathVariable Long userId){
        // 해당 유저의 project들의 project_id와 title 반환
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        boolean deleted = userService.deleteUser(userId);   //userService 작성해야함
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
