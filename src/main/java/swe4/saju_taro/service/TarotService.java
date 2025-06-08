package swe4.saju_taro.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.common.status.ErrorStatus;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.dto.TarotRequestDTO;
import swe4.saju_taro.dto.TarotResponseDTO;
import swe4.saju_taro.dto.TarotSaveRequestDTO;
import swe4.saju_taro.repository.ConsultationRepository;
import swe4.saju_taro.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class TarotService {

    private final ConsultationRepository consultationRepository;
    private final ProjectRepository projectRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    final String aiUrl = "http://localhost:5000/api/tarot/consult";

    public TarotResponseDTO tarotConsult(TarotRequestDTO requestDTO) {

        if (requestDTO.getUserId() == null || requestDTO.getProjectId() == null ||
                requestDTO.getQuestion() == null || requestDTO.getQuestion().trim().isEmpty()) {
            throw new GeneralException(ErrorStatus.MISSING_REQUIRED_VALUE);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", requestDTO.getUserId());
            requestBody.put("project_id", requestDTO.getProjectId());
            requestBody.put("cards", project.getTarotCards() != null ? project.getTarotCards() : "아직 뽑은 카드 없음");
            requestBody.put("question", requestDTO.getQuestion());

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    aiUrl,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map<?, ?> body = response.getBody();
            if (body == null || !Boolean.TRUE.equals(body.get("isSuccess"))) {
                throw new GeneralException(ErrorStatus.AI_RESPONSE_FAILED);
            }

            Map<String, Object> resultMap = (Map<String, Object>) body.get("result");
            String consultResult = (String) resultMap.get("result");

            Consultation consultation = Consultation.builder()
                    .project(project)
                    .question(requestDTO.getQuestion())
                    .result(consultResult)
                    .build();

            project.setUpdatedAt(LocalDateTime.now());

            consultationRepository.save(consultation);

            return new TarotResponseDTO(
                    consultation.getConsultationId(),
                    consultation.getQuestion(),
                    consultation.getResult(),
                    consultation.getCreatedAt()
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.AI_RESPONSE_FAILED);
        }
    }

    public void tarotSave(TarotSaveRequestDTO requestDTO) {
        if (requestDTO.getProjectId() == null || requestDTO.getCards() == null) {
            throw new GeneralException(ErrorStatus.MISSING_REQUIRED_VALUE);
        }

        Project project = projectRepository.findById(requestDTO.getProjectId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));

        project.setTarotCards(requestDTO.getCards().toString());
    }
}
