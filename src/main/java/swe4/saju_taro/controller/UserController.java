package swe4.saju_taro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.common.ApiResponse;
import swe4.saju_taro.common.status.SuccessStatus;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.common.CommonResponse;
import swe4.saju_taro.dto.UserRequestDTO;
import swe4.saju_taro.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/new")
    public ApiResponse<?> newUser(@RequestBody UserRequestDTO.UserDTO userDTO){
        return ApiResponse.onSuccess(SuccessStatus.USER_CREATED, userService.createUser(userDTO));
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<User> getUserInfo(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/change/{userId}")
    public ApiResponse<?> changeUserInfo(@PathVariable("userId") Integer userId, @RequestBody UserRequestDTO.UserDTO userDTO){

        userService.updateUser(userId, userDTO);
        return ApiResponse.onSuccess(SuccessStatus.USER_CHANGEED, null);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable("userId") Integer userId) {
        boolean deleted = userService.deleteUser(userId);   //userService 작성해야함
        if (deleted) {
            return ResponseEntity.ok(new CommonResponse<>(true, "COMMON200", "유저 삭제에 성공했습니다."));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new CommonResponse<>(false, "COMMON404", "해당 유저를 찾을 수 없어 삭제에 실패했습니다."));
        }
    }



//    @GetMapping("/list/{userId}/projects")
//    public List<ProjectTitle> listProjects(@PathVariable Long userId){
//        // 해당 유저의 project들의 project_id와 title 반환
//    }
}
