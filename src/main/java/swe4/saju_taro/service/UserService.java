package swe4.saju_taro.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.dto.UserRequest;
import swe4.saju_taro.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUser(Integer id) {
        return userRepository.findById(id);
    }

    @Transactional
    public boolean createUser (User user){
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public boolean deleteUser(Integer userId) {
        return userRepository.deleteById(userId);
    }

    @Transactional
    public boolean updateUser(Integer userId, UserRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return false;

        User user = optionalUser.get();
        user.setBirth(request.getBirth());
        user.setTime(request.getTime());
        user.setGender(request.isGender());

        userRepository.save(user);
        return true;
    }
}
