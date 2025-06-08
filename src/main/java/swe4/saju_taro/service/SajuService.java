package swe4.saju_taro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.common.status.ErrorStatus;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.dto.SajuRequestDTO;
import swe4.saju_taro.dto.SajuResponseDTO;
import swe4.saju_taro.repository.ConsultationRepository;
import swe4.saju_taro.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class SajuService {

    private final ConsultationRepository consultationRepository;
    private final ProjectRepository projectRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    final String aiUrl = "http://localhost:5000/api/saju/consult";

    public SajuResponseDTO sajuConsult(SajuRequestDTO requestDTO) {

        if (requestDTO.getUserId() == null || requestDTO.getProjectId() == null ||
                requestDTO.getQuestion() == null || requestDTO.getQuestion().trim().isEmpty() ||
                requestDTO.getSajuData() == null) {
            throw new GeneralException(ErrorStatus.MISSING_REQUIRED_VALUE);
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("user_id", requestDTO.getUserId());
            requestBody.put("project_id", requestDTO.getProjectId());
            requestBody.put("question", requestDTO.getQuestion());
            requestBody.put("sajuData", requestDTO.getSajuData());

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

            Project project = projectRepository.findById(requestDTO.getProjectId())
                    .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));

            Consultation consultation = Consultation.builder()
                    .project(project)
                    .question(requestDTO.getQuestion())
                    .result(consultResult)
                    .build();

            project.setUpdatedAt(LocalDateTime.now());

            consultationRepository.save(consultation);

            return new SajuResponseDTO(
                    consultation.getConsultationId(),
                    consultation.getQuestion(),
                    consultation.getResult(),
                    consultation.getCreatedAt()
            );
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.AI_RESPONSE_FAILED);
        }
    }
}
