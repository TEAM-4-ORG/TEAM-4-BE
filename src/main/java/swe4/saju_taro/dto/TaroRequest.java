package swe4.saju_taro.dto;

import java.util.ArrayList;
import java.util.List;

public class TaroRequest {
    private Long user_id;
    private Long project_id;

    private List<String> cards = new ArrayList<>();

    private String question;
}
