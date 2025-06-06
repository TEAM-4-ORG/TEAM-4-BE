package swe4.saju_taro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import swe4.saju_taro.domain.Consultation;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConsultationResponseDTO {

    @JsonProperty("consultation_id")
    private Integer consultationId;
    private String question;
    private String result;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    public static ConsultationResponseDTO from(Consultation consultation) {
        return new ConsultationResponseDTO(
                consultation.getConsultationId(),
                consultation.getQuestion(),
                consultation.getResult(),
                consultation.getCreatedAt()
        );
    }
}
