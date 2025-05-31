package swe4.saju_taro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Consultation {
    @Id
    private Long consultation_id;

    private Long user_id;
    private Long project_id;

    private String question;
    private String result;
    private String created_at;
}
