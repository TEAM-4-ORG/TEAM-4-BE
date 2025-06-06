package swe4.saju_taro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe4.saju_taro.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
