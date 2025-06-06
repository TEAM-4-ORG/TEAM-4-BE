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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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

    public Optional<User> getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return userRepository.findById(id);
    }

    public boolean deleteUser(Integer userId) {
        return userRepository.deleteById(userId);
    }

    public void updateUser(Integer userId, UserRequestDTO.UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findById(userId);
        //if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        user.setBirthDate(userDTO.getParsedBirth());
        user.setBirthTime(userDTO.getParsedTime());
        user.setGender(userDTO.isGender());

        userRepository.save(user);
        //return true;
    }
}
