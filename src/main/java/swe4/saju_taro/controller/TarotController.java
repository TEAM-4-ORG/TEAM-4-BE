package swe4.saju_taro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swe4.saju_taro.common.ApiResponse;
import swe4.saju_taro.common.status.SuccessStatus;
import swe4.saju_taro.dto.TarotRequestDTO;
import swe4.saju_taro.dto.TarotSaveRequestDTO;
import swe4.saju_taro.service.TarotService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tarot")
public class TarotController {

    private final TarotService tarotService;

    @PostMapping("/consult")
    public ApiResponse<?> sajuConsult(@RequestBody TarotRequestDTO request) {
        return ApiResponse.onSuccess(SuccessStatus._OK, tarotService.tarotConsult(request));
    }

    @PostMapping("/save")
    public ApiResponse<?> tarotSave(@RequestBody TarotSaveRequestDTO request) {
        tarotService.tarotSave(request);
        return ApiResponse.onSuccess(SuccessStatus.TAROT_SAVED, null);
    }
}
