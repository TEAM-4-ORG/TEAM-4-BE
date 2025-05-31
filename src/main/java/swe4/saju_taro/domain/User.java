package swe4.saju_taro.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    private Long user_id;

    private String birth;
    private String time;
    private boolean gender;

    private String ilgan;
    private String ilju;
    private String ilji;
    private String oheng;
    private String sibsin;

    private List<Project> projects = new ArrayList<>();
}
