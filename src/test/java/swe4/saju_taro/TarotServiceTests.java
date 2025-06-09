
package swe4.saju_taro;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.common.status.ErrorStatus;
import swe4.saju_taro.domain.*;
import swe4.saju_taro.dto.*;
import swe4.saju_taro.repository.*;
import swe4.saju_taro.service.TarotService;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarotServiceTest {

    @InjectMocks
    private TarotService tarotService;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Spy
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Project mockProject;
    private Consultation mockConsultation;

    @BeforeEach
    void setup() throws Exception {
        tarotService = new TarotService(consultationRepository, projectRepository);
        mockProject = Project.builder()
                .projectId(1)
                .tarotCards("[카드1, 카드2]")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        mockConsultation = Consultation.builder()
                .consultationId(100)
                .question("카드 결과는?")
                .result("좋은 결과입니다.")
                .project(mockProject)
                .createdAt(LocalDateTime.now())
                .build();

        Field restTemplateField = TarotService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(tarotService, restTemplate);
    }

    @Test
    void tarotConsult_shouldReturnResponseDTO_whenValidInput() throws Exception {
        TarotRequestDTO requestDTO = new TarotRequestDTO(1, 1, "질문");

        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        Map<String, Object> mockResult = Map.of(
                "consultation_id", 100,
                "question", "질문",
                "result", "좋은 결과입니다.",
                "created_at", "2025-06-10T04:12"
        );

        Map<String, Object> mockResponse = Map.of(
                "isSuccess", true,
                "result", mockResult
        );
        ResponseEntity<Map> mockEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        doReturn(mockEntity).when(restTemplate).exchange(
                eq("http://localhost:5000/api/tarot/consult"),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(Map.class)
        );

        when(consultationRepository.save(any(Consultation.class)))
                .thenAnswer(invocation -> {
                    Consultation saved = invocation.getArgument(0);
                    saved.setConsultationId(100);
                    return saved;
                });

        TarotResponseDTO result = tarotService.tarotConsult(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getConsultationId()).isEqualTo(100);
        assertThat(result.getResult()).isEqualTo("좋은 결과입니다.");
    }

    @Test
    void tarotConsult_shouldThrowException_whenMissingValues() {
        TarotRequestDTO requestDTO = new TarotRequestDTO(null, null, "");

        GeneralException ex = assertThrows(GeneralException.class, () -> tarotService.tarotConsult(requestDTO));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getErrorReasonHttpStatus().getHttpStatus());
        assertEquals("COMMON400", ex.getErrorReasonHttpStatus().getCode());
        assertEquals("입력되지 않은 필수값이 있습니다.", ex.getErrorReasonHttpStatus().getMessage());
    }

    @Test
    void tarotSave_shouldUpdateTarotCards_whenValidInput() {
        TarotSaveRequestDTO requestDTO = new TarotSaveRequestDTO(1, List.of("카드1", "카드2"));

        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        tarotService.tarotSave(requestDTO);

        verify(projectRepository).findById(1);
        assertEquals("[카드1, 카드2]", mockProject.getTarotCards());
    }

    @Test
    void tarotSave_shouldThrowException_whenMissingValues() {
        TarotSaveRequestDTO invalidRequest = new TarotSaveRequestDTO(null, null);

        GeneralException ex = assertThrows(GeneralException.class, () -> tarotService.tarotSave(invalidRequest));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getErrorReasonHttpStatus().getHttpStatus());
        assertEquals("COMMON400", ex.getErrorReasonHttpStatus().getCode());
        assertEquals("입력되지 않은 필수값이 있습니다.", ex.getErrorReasonHttpStatus().getMessage());
    }
}
