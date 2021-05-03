package myapp.featureorigami;

import myapp.dto.TaskResponseDto;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_DOWN;

public class OrigamiResponseDto {

    private final static Integer HOURS_IN_DAY = 7;
    private final static Integer MINUTES_IN_HOUR = 60;

    private String userName;

    private String origami;

    private Integer durationInMin;

    public OrigamiResponseDto(TaskResponseDto taskResponseDto) {
        this.userName = taskResponseDto.getUserName();
        this.origami = taskResponseDto.getOrigami();
        this.durationInMin = taskResponseDto.getDurationInMin();
    }

    public OrigamiResponseDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrigami() {
        return origami;
    }

    public Integer getDurationInMin() {
        return durationInMin;
    }

    public BigDecimal getDurationInDays(){
        BigDecimal minutesInDay = BigDecimal.valueOf(HOURS_IN_DAY * MINUTES_IN_HOUR);
        return BigDecimal.valueOf(durationInMin).setScale(2, ROUND_DOWN).divide(minutesInDay, ROUND_DOWN);
    }

    public void setOrigami(String origami) {
        this.origami = origami;
    }

    public void setDurationInMin(Integer durationInMin) {
        this.durationInMin = durationInMin;
    }

    @Override
    public String toString() {
        return "OrigamiResponseDto{" +
                "userName='" + userName + '\'' +
                ", origami='" + origami + '\'' +
                ", durationInMin=" + durationInMin +
                '}';
    }
}
