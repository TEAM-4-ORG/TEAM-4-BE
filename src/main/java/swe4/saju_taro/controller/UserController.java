package swe4.saju_taro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.CommonResponse;
import swe4.saju_taro.dto.UserRequest;
import swe4.saju_taro.service.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable("userId") Integer userId) {
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

        boolean created = userService.createUser(user);

        if (created) {
            return ResponseEntity.ok(new CommonResponse(true, "COMMON200", "유저 추가에 성공했습니다."));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CommonResponse(false, "USER500", "유저 추가에 실패했습니다."));
        }
    }

    @PutMapping("/change/{userId}")
    public ResponseEntity<CommonResponse> changeUserInfo(@PathVariable("userId") Integer userId, @RequestBody UserRequest request){
        boolean updated = userService.updateUser(userId, request);

        if (updated) {
            return ResponseEntity.ok(
                    new CommonResponse(true, "COMMON200", "유저 정보 수정에 성공했습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(false, "USER404", "해당 유저를 찾을 수 없습니다."));
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<CommonResponse> deleteUser(@PathVariable("userId") Integer userId) {
        boolean deleted = userService.deleteUser(userId);   //userService 작성해야함
        if (deleted) {
            return ResponseEntity.ok(new CommonResponse(true, "COMMON200", "유저 삭제에 성공했습니다."));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse(false, "COMMON404", "해당 유저를 찾을 수 없어 삭제에 실패했습니다."));
        }
    }
//    @GetMapping("/list/{userId}/projects")
//    public List<ProjectTitle> listProjects(@PathVariable Long userId){
//        // 해당 유저의 project들의 project_id와 title 반환
//    }
}
