package swe4.saju_taro.controller;

import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.SajuRequest;


@RestController
@RequestMapping("/api/saju")
public class SajuController {

    @GetMapping("/info")
    public User getSajuInfo(@RequestParam Long user_id) {
        // userId로 DB에서 해당 유저의 사주정보를 가져온다
    }

    @PostMapping("/consult")
    public Consultation consult(@RequestBody SajuRequest request) {
        // SajuRequest(userid, projectid, 사주정보, question) LLM에 넘겨서 결과 반환
        // 상담기록 DB에 저장
    }

}
