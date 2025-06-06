package swe4.saju_taro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonPropertyOrder({ "user_id", "birth", "time", "gender", "projects" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Integer userId;

    @Column(name = "birth_date", nullable = false)
    @JsonProperty("birth")
    private LocalDate birthDate;

    @Column(name = "birth_time")
    @JsonProperty("time")
    private LocalTime birthTime;

    @Column(nullable = false)
    @JsonProperty("gender")
    private Boolean gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty("projects")
    private List<Project> projects = new ArrayList<>();

}
