package swe4.saju_taro.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.dto.TarotRequest;

@RestController
@RequestMapping("/api/tarot")
public class TarotController {

    @PostMapping("/consult")
    public Consultation tarotConsult(@RequestBody TarotRequest request){
        //타로 카드 LLM에 넘겨서 해석결과 반환
    }
}
