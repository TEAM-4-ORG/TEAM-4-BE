package swe4.saju_taro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swe4.saju_taro.domain.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation, Integer> {
}
