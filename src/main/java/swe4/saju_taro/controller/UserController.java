package swe4.saju_taro.controller;

import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.UserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    public User getUserInfo(@RequestParam Long user_id){
        // DB에서 User 가져옴
    }

    @PostMapping("/new")
    public Long newUser(@RequestBody UserRequest request){
        // UserRequest DB 저장후 userid 반환
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestParam Long user_id){
        //사주정보 초기화
        //DB에서 해당 userid 삭제
    }
}
