package swe4.saju_taro.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "birth_time")
    private LocalTime birthTime;

    @Column(nullable = false)
    private Boolean gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    public void setBirth(Object birth) {
        if (birth instanceof String) {
            this.birthDate = LocalDate.parse((String) birth); // e.g. "2000-12-19"
        } else if (birth instanceof LocalDate) {
            this.birthDate = (LocalDate) birth;
        } else {
            throw new IllegalArgumentException("Invalid birth type: " + birth);
        }
    }

    public void setTime(Object time) {
        if (time instanceof String) {
            this.birthTime = LocalTime.parse((String) time); // e.g. "11:45"
        } else if (time instanceof LocalTime) {
            this.birthTime = (LocalTime) time;
        } else {
            throw new IllegalArgumentException("Invalid time type: " + time);
        }
    }

    public void setGender(Object gender) {
        if (gender instanceof Boolean) {
            this.gender = (Boolean) gender;
        } else if (gender instanceof String) {
            this.gender = Boolean.parseBoolean((String) gender);
        } else {
            throw new IllegalArgumentException("Invalid gender type: " + gender);
        }
    }
}
