package swe4.saju_taro.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.common.status.ErrorStatus;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.UserRequestDTO;
import swe4.saju_taro.dto.UserResponseDTO;
import swe4.saju_taro.repository.UserRepository;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {

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

    public User getUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
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
