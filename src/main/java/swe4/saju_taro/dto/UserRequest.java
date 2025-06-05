package swe4.saju_taro.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserRequest {

    private LocalDate birth;
    private LocalTime time;
    private boolean gender;

    public UserRequest() {
    }

    public UserRequest(LocalDate birth, LocalTime time, boolean gender) {
        this.birth = birth;
        this.time = time;
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalTime getTime() {
        return time;
    }

    public boolean isGender() {
        return gender;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

//    private String ilgan;
//    private String ilju;
//    private String ilji;
//    private String oheng;
//    private String sibsin;
}
