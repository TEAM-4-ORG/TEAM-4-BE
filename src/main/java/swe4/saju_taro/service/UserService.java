package swe4.saju_taro.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import swe4.saju_taro.domain.User;
import swe4.saju_taro.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}
