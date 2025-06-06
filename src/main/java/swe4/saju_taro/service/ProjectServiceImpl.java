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
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.ConsultationResponseDTO;
import swe4.saju_taro.dto.ProjectRequestDTO;
import swe4.saju_taro.dto.ProjectResponseDTO;
import swe4.saju_taro.repository.ConsultationRepository;
import swe4.saju_taro.repository.ProjectRepository;
import swe4.saju_taro.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ConsultationRepository consultationRepository;

    public ProjectResponseDTO.ProjectCreateDTO createNewProject(ProjectRequestDTO.ProjectDTO projectDTO) {

        if (projectDTO.getUserId() == null || projectDTO.getType() == null ||
                projectDTO.getFirstQuestion() == null || projectDTO.getFirstQuestion().trim().isEmpty()) {
            throw new GeneralException(ErrorStatus.MISSING_REQUIRED_VALUE);
        }

        User user = userRepository.findById(projectDTO.getUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        // AI 기반 제목 유추
        String aiTitle = getTitleFromAI(projectDTO.getFirstQuestion());

        Project project = Project.builder()
                .user(user)
                .type(projectDTO.getType())
                .title(aiTitle)
                .build();
        projectRepository.save(project);

        Consultation consultation = Consultation.builder()
                .project(project)
                .question(projectDTO.getFirstQuestion())
                .result("")
                .build();
        consultationRepository.save(consultation);

        return new ProjectResponseDTO.ProjectCreateDTO(aiTitle);
    }

    @Override
    public ProjectResponseDTO.ProjectLoadDTO loadProject(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));

        User user = project.getUser();

        List<ConsultationResponseDTO> consultations = project.getConsultations().stream()
                .sorted(Comparator.comparing(Consultation::getCreatedAt).reversed())
                .map(ConsultationResponseDTO::from)
                .toList();

        return new ProjectResponseDTO.ProjectLoadDTO(
                project.getProjectId(),
                user.getUserId(),
                project.getTitle(),
                project.getType().name(),
                consultations
        );
    }

    @Override
    public void deleteProject(Integer projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.PROJECT_NOT_FOUND));

        projectRepository.delete(project);
    }

    private String getTitleFromAI(String question) {
        final String defaultTitle = "새 프로젝트";
        final String aiUrl = "http://localhost:5000/api/project/new";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = Map.of("first_question", question);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> response = restTemplate.exchange(
                    aiUrl,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            Map<String, Object> body = response.getBody();
            if (body != null && Boolean.TRUE.equals(body.get("isSuccess"))) {
                Map<String, Object> result = (Map<String, Object>) body.get("result");
                Object title = result.get("title");
                return title != null ? title.toString() : defaultTitle;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultTitle;
    }
}
