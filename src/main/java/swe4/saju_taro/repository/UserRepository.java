package swe4.saju_taro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe4.saju_taro.domain.Project;
import swe4.saju_taro.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
}