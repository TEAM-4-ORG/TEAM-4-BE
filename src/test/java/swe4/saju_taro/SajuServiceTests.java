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
import swe4.saju_taro.service.SajuService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SajuServiceTest {

    @InjectMocks
    private SajuService sajuService;

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private User mockUser;
    private Project mockProject;
    private Consultation mockConsultation;

    @BeforeEach
    void setup() throws Exception {

        mockUser = User.builder()
                .userId(1)
                .birthDate(LocalDate.of(1999, 1, 1))
                .birthTime(LocalTime.of(10, 30))
                .gender(true)
                .sajuData(objectMapper.writeValueAsString(Map.of("birth", "1999-01-01")))
                .build();


        mockProject = Project.builder()
                .projectId(1)
                .title("테스트 프로젝트")
                .type(Type.SAJU)
                .user(mockUser)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        mockConsultation = Consultation.builder()
                .consultationId(100)
                .question("오늘의 운세?")
                .result("길합니다")
                .project(mockProject)
                .createdAt(LocalDateTime.now())
                .build();
        Field restTemplateField = SajuService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(sajuService, restTemplate);
    }

    @Test
    void sajuConsult_shouldReturnResponseDTO_whenValidInput() throws Exception {
        // given
        SajuRequestDTO requestDTO = new SajuRequestDTO(1, 1, "질문");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(projectRepository.findById(1)).thenReturn(Optional.of(mockProject));

        Map<String, Object> mockResult = Map.of(
                "consultation_id", 100,
                "question", "질문",
                "result", "길합니다",
                "created_at", "2025-06-10T04:12"
        );

        Map<String, Object> mockResponse = Map.of(
                "isSuccess", true,
                "result", mockResult
        );
        ResponseEntity<Map> mockEntity = new ResponseEntity<>(mockResponse, HttpStatus.OK);
        doReturn(mockEntity).when(restTemplate).exchange(
                eq("http://localhost:5000/api/saju/consult"),
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

        // when
        SajuResponseDTO result = sajuService.sajuConsult(requestDTO);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getConsultationId()).isEqualTo(100);
        assertThat(result.getResult()).isEqualTo("길합니다");
    }


    @Test
    void sajuConsult_shouldThrowException_whenMissingValues() {
        // given
        SajuRequestDTO requestDTO = new SajuRequestDTO(null, null, "");

        // when
        GeneralException ex = assertThrows(GeneralException.class, () -> sajuService.sajuConsult(requestDTO));

        // then
        assertEquals(HttpStatus.BAD_REQUEST, ex.getErrorReasonHttpStatus().getHttpStatus());
        assertEquals("COMMON400", ex.getErrorReasonHttpStatus().getCode());
        assertEquals("입력되지 않은 필수값이 있습니다.", ex.getErrorReasonHttpStatus().getMessage());
    }


    @Test
    void sajuSave_shouldUpdateSajuData_whenValidInput() throws Exception {
        // given
        Map<String, Object> sajuData = Map.of("birth", "2000-12-31");
        SajuSaveRequestDTO requestDTO = new SajuSaveRequestDTO(1, sajuData);

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // when
        sajuService.sajuSave(requestDTO);

        // then
        verify(userRepository).findById(1);
        assertEquals(objectMapper.writeValueAsString(sajuData), mockUser.getSajuData());
    }


    @Test
    void sajuSave_shouldThrowException_whenMissingValues() {
        // given
        SajuSaveRequestDTO invalidRequest = new SajuSaveRequestDTO(null, null);

        // when
        GeneralException ex = assertThrows(GeneralException.class, () -> sajuService.sajuSave(invalidRequest));

        // then
        assertEquals(HttpStatus.BAD_REQUEST, ex.getErrorReasonHttpStatus().getHttpStatus());
        assertEquals("COMMON400", ex.getErrorReasonHttpStatus().getCode());
        assertEquals("입력되지 않은 필수값이 있습니다.", ex.getErrorReasonHttpStatus().getMessage());
    }

}
