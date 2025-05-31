package swe4.saju_taro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    private Long project_id;

    private Long user_id;

    private List<Consultation> consultations = new ArrayList<>();
}
