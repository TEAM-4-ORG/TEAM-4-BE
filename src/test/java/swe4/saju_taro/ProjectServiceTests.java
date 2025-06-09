package swe4.saju_taro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.Type;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.ProjectRequestDTO;
import swe4.saju_taro.dto.ProjectResponseDTO;
import swe4.saju_taro.repository.ConsultationRepository;
import swe4.saju_taro.repository.ProjectRepository;
import swe4.saju_taro.repository.UserRepository;
import swe4.saju_taro.service.ProjectServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConsultationRepository consultationRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private User mockUser;

    @BeforeEach
    void setup() {
        mockUser = User.builder()
                .userId(1)
                .build();
    }

    @Test
    void createNewProject_shouldSucceed_withValidInput() {
        ProjectRequestDTO.ProjectDTO dto = new ProjectRequestDTO.ProjectDTO(1, Type.TAROT, "오늘의 운세는?");

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> {
            Project p = invocation.getArgument(0);
            p.setProjectId(1); // 수동 ID 설정
            return p;
        });

        ProjectResponseDTO.ProjectCreateDTO result = projectService.createNewProject(dto);

        assertEquals(1, result.getProjectId());
        assertNotNull(result.getTitle());
    }

    @Test
    void createNewProject_shouldThrow_whenMissingRequiredFields() {
        ProjectRequestDTO.ProjectDTO dto = new ProjectRequestDTO.ProjectDTO();

        assertThrows(GeneralException.class, () -> projectService.createNewProject(dto));
    }

    @Test
    void loadProject_shouldReturnProjectData_whenProjectExists() {
        Project project = Project.builder()
                .projectId(10)
                .user(mockUser)
                .title("테스트 프로젝트")
                .type(Type.TAROT)
                .consultations(java.util.List.of())
                .build();

        when(projectRepository.findById(10)).thenReturn(Optional.of(project));

        ProjectResponseDTO.ProjectLoadDTO dto = projectService.loadProject(10);

        assertEquals(10, dto.getProjectId());
        assertEquals("테스트 프로젝트", dto.getTitle());
        verify(projectRepository).findById(10);
    }

    @Test
    void loadProject_shouldThrow_whenProjectNotFound() {
        when(projectRepository.findById(99)).thenReturn(Optional.empty());

        GeneralException ex = assertThrows(GeneralException.class, () -> projectService.loadProject(99));
        assertEquals(HttpStatus.NOT_FOUND, ex.getErrorReasonHttpStatus().getHttpStatus());
        assertEquals("PROJECT404", ex.getErrorReasonHttpStatus().getCode());
        assertEquals("해당 프로젝트를 찾을 수 없습니다.", ex.getErrorReasonHttpStatus().getMessage());
    }

    @Test
    void deleteProject_shouldSucceed_whenProjectExists() {
        Project project = Project.builder().projectId(5).build();
        when(projectRepository.findById(5)).thenReturn(Optional.of(project));

        projectService.deleteProject(5);

        verify(projectRepository).delete(project);
    }

    @Test
    void deleteProject_shouldThrow_whenProjectNotFound() {
        when(projectRepository.findById(77)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> projectService.deleteProject(77));
    }
}
