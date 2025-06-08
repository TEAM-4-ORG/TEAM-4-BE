package swe4.saju_taro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.UserResponseDTO;
import swe4.saju_taro.repository.UserRepository;
import swe4.saju_taro.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void getUser_shouldReturnUserLoadDTO_withSortedProjects() {
        // given
        Project project1 = Project.builder()
                .title("프로젝트1")
                .updatedAt(LocalDateTime.of(2024, 1, 1, 0, 0))
                .build();

        Project project2 = Project.builder()
                .title("프로젝트2")
                .updatedAt(LocalDateTime.of(2025, 1, 1, 0, 0))
                .build();

        List<Project> projectList = List.of(project1, project2);

        User mockUser = User.builder()
                .userId(1)
                .birthDate(LocalDate.of(2000, 12, 19))
                .birthTime(LocalTime.parse("11:45"))
                .gender(true)
                .projects(projectList)
                .build();

        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));

        // when
        UserResponseDTO.UserLoadDTO result = userService.getUser(1);

        // then
        assertEquals(1, result.getUser_id());
        assertEquals(LocalDate.of(2000, 12, 19), result.getBirth());
        assertEquals(LocalTime.of(11, 45), result.getTime());
        assertTrue(result.isGender());
        assertEquals(2, result.getProjects().size());

        // 정렬 순서 확인
        assertEquals("프로젝트2", result.getProjects().get(0).getTitle()); // 최신 업데이트 프로젝트
        assertEquals("프로젝트1", result.getProjects().get(1).getTitle()); // 과거 업데이트 프로젝트
    }
}