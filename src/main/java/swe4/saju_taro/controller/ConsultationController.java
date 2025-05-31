package swe4.saju_taro.controller;

import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.dto.TarotRequest;

@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

    @PostMapping("/consult")
    public Consultation consult(@RequestBody TarotRequest request){
        // llm에 전달 및 db 저장
    }
}
