package swe4.saju_taro.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swe4.saju_taro.common.ApiResponse;
import swe4.saju_taro.common.status.SuccessStatus;
import swe4.saju_taro.dto.SajuRequestDTO;
import swe4.saju_taro.dto.SajuSaveRequestDTO;
import swe4.saju_taro.service.SajuService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/saju")
public class SajuController {

    private final SajuService sajuService;

    @PostMapping("/consult")
    public ApiResponse<?> sajuConsult(@RequestBody SajuRequestDTO request) {
        return ApiResponse.onSuccess(SuccessStatus._OK, sajuService.sajuConsult(request));
    }

    @PostMapping("/save")
    public ApiResponse<?> sajuSave(@RequestBody SajuSaveRequestDTO request) {
        sajuService.sajuSave(request);
        return ApiResponse.onSuccess(SuccessStatus.SAJU_SAVED, null);
    }
}
