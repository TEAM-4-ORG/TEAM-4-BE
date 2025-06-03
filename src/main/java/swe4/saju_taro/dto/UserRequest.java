package swe4.saju_taro.dto;

public class UserRequest {

    private String birth;
    private String time;
    private boolean gender;

    public UserRequest() {
    }

    public UserRequest(String birth, String time, boolean gender) {
        this.birth = birth;
        this.time = time;
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public String getTime() {
        return time;
    }

    public boolean isGender() {
        return gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setTime(String time) {
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
