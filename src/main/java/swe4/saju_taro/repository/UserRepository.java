package swe4.saju_taro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe4.saju_taro.domain.User;

import java.util.Optional;

public interface UserRepository{
    // 자동 메서드
    Optional<User> findById(Long id);

}