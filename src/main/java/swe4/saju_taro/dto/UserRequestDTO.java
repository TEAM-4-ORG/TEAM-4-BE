package swe4.saju_taro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swe4.saju_taro.common.GeneralException;
import swe4.saju_taro.common.status.ErrorStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class UserRequestDTO {


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserDTO{
        private String birth;
        private String time;
        private boolean gender;

        public LocalDate getParsedBirth() {
            try {
                return LocalDate.parse(birth);
            } catch (DateTimeParseException e) {
                throw new GeneralException(ErrorStatus.USER_WRONG_BIRTH);
            }
        }

        public LocalTime getParsedTime() {
            try {
                return LocalTime.parse(time);
            } catch (DateTimeParseException e) {
                throw new GeneralException(ErrorStatus.USER_WRONG_TIME);
            }
        }
    }



}
