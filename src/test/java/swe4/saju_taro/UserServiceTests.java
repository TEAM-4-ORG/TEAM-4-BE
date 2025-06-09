package swe4.saju_taro;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.UserRequestDTO;
import swe4.saju_taro.dto.UserResponseDTO;
import swe4.saju_taro.repository.UserRepository;
import swe4.saju_taro.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserRequestDTO.UserDTO validUserDTO;

    @BeforeEach
    void setUp(){
        validUserDTO = new UserRequestDTO.UserDTO();
        validUserDTO.setBirth("2000-01-01");
        validUserDTO.setTime("12:00");
        validUserDTO.setGender(true);
    }

    @Test
    void createUser_shouldSaveAndReturnId() {
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        UserResponseDTO.UserDTO result = userService.createUser(validUserDTO);

        verify(userRepository).save(captor.capture());
        User savedUser = captor.getValue();
        assertEquals(LocalDate.of(2000, 1, 1), savedUser.getBirthDate());
        assertEquals(LocalTime.of(12, 0), savedUser.getBirthTime());
        assertTrue(savedUser.getGender());
        assertNotNull(result);
    }

    @Test
    void createUser_shouldThrowException_whenMissingValues() {
        UserRequestDTO.UserDTO invalidDTO = new UserRequestDTO.UserDTO(); // 모두 null
        assertThrows(GeneralException.class, () -> userService.createUser(invalidDTO));
    }

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

    @Test
    void getUser_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        GeneralException ex = assertThrows(GeneralException.class, () -> userService.getUser(99));
        assertEquals(HttpStatus.NOT_FOUND, ex.getErrorReasonHttpStatus().getHttpStatus());
        assertEquals("USER404", ex.getErrorReasonHttpStatus().getCode());
        assertEquals("사용자를 찾을 수 없습니다.", ex.getErrorReasonHttpStatus().getMessage());
    }

    @Test
    void deleteUser_shouldDeleteUser_whenUserExists() {
        User user = User.builder().userId(10).build();
        when(userRepository.findById(10)).thenReturn(Optional.of(user));

        userService.deleteUser(10);

        verify(userRepository).deleteById(10);
    }


    @Test
    void updateUser_shouldUpdateFields() {
        User user = User.builder().userId(5).build();
        when(userRepository.findById(5)).thenReturn(Optional.of(user));

        UserRequestDTO.UserDTO updateDTO = new UserRequestDTO.UserDTO();
        updateDTO.setBirth("1990-10-10");
        updateDTO.setTime("23:45");
        updateDTO.setGender(true);

        userService.updateUser(5, updateDTO);

        assertEquals(LocalDate.of(1990, 10, 10), user.getBirthDate());
        assertEquals(LocalTime.of(23, 45), user.getBirthTime());
        assertTrue(user.getGender());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(777)).thenReturn(Optional.empty());

        assertThrows(GeneralException.class, () -> userService.updateUser(777, validUserDTO));
    }
}