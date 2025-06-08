package swe4.saju_taro.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.common.status.ErrorStatus;
import swe4.saju_taro.domain.Consultation;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.ConsultationResponseDTO;
import swe4.saju_taro.dto.ProjectResponseDTO;
import swe4.saju_taro.dto.UserRequestDTO;
import swe4.saju_taro.dto.UserResponseDTO;
import swe4.saju_taro.repository.UserRepository;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO.UserDTO createUser (UserRequestDTO.UserDTO userDTO){

        if (userDTO.getBirth() == null || userDTO.getTime() == null){
            throw new GeneralException(ErrorStatus.MISSING_REQUIRED_VALUE);
        }

        User user = User.builder()
                .birthDate(userDTO.getParsedBirth())
                .birthTime(userDTO.getParsedTime())
                .gender(userDTO.isGender())
                .build();
        userRepository.save(user);

        return new UserResponseDTO.UserDTO(user.getUserId());
    }

    public UserResponseDTO.UserLoadDTO getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<ProjectResponseDTO.ProjectUserLoadDTO> projects = user.getProjects().stream()
                .sorted(Comparator.comparing(Project::getUpdatedAt).reversed())
                .map(ProjectResponseDTO.ProjectUserLoadDTO::new)
                .toList();


        return new UserResponseDTO.UserLoadDTO(
                user.getUserId(),
                user.getBirthDate(),
                user.getBirthTime(),
                user.getGender(),
                projects
        );
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        userRepository.deleteById(userId);
    }

    public void updateUser(Integer userId, UserRequestDTO.UserDTO userDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        user.setBirthDate(userDTO.getParsedBirth());
        user.setBirthTime(userDTO.getParsedTime());
        user.setGender(userDTO.isGender());

        userRepository.save(user);
    }
}
