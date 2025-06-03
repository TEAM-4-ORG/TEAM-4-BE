package swe4.saju_taro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.CommonResponse;
import swe4.saju_taro.dto.ProjectTitle;
import swe4.saju_taro.dto.UserRequest;
import swe4.saju_taro.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<CommonResponse> newUser(@RequestBody UserRequest request){
        User user = new User();
        user.setBirth(request.getBirth());
        user.setTime(request.getTime());
        user.setGender(request.isGender());

        userService.createUser(user);

        return ResponseEntity.ok(new CommonResponse(true, "COMMON200", "유저 추가에 성공했습니다."));
    }

//    @PutMapping("/change/{userId}")
//    public Long changeUserInfo(@PathVariable Long userId, @RequestBody UserRequest request){
//        // userid, UserRequest(사주 정보) 를 받아 DB 변경후 user_id 반환
//    }
//
//    @GetMapping("/list/{userId}/projects")
//    public List<ProjectTitle> listProjects(@PathVariable Long userId){
//        // 해당 유저의 project들의 project_id와 title 반환
//    }
//
//    @DeleteMapping("/delete/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
//        boolean deleted = userService.deleteUser(userId);   //userService 작성해야함
//        if (deleted) {
//            return ResponseEntity.noContent().build(); // 204 No Content
//        } else {
//            return ResponseEntity.notFound().build(); // 404 Not Found
//        }
//    }
}
