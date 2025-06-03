package swe4.saju_taro.service;

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

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
