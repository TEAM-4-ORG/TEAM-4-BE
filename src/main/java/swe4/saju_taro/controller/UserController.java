package swe4.saju_taro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.common.ApiResponse;
import swe4.saju_taro.common.status.SuccessStatus;
import swe4.saju_taro.domain.User;
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
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping("/change/{userId}")
    public ApiResponse<?> changeUserInfo(@PathVariable("userId") Integer userId, @RequestBody UserRequestDTO.UserDTO userDTO){
        userService.updateUser(userId, userDTO);
        return ApiResponse.onSuccess(SuccessStatus.USER_CHANGEED, null);
    }

    @DeleteMapping("/delete/{userId}")
    public ApiResponse<?> deleteUser(@PathVariable("userId") Integer userId) {
        userService.deleteUser(userId);
        return ApiResponse.onSuccess(SuccessStatus.USER_DELETED, null);
    }
}
