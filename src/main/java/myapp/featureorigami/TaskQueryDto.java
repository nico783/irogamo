package myapp.featureorigami;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class TaskQueryDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateMin;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateMax;

    private String userName;

    public LocalDate getDateMin() {
        return dateMin;
    }

    public void setDateMin(LocalDate dateMin) {
        this.dateMin = dateMin;
    }

    public LocalDate getDateMax() {
        return dateMax;
    }

    public void setDateMax(LocalDate dateMax) {
        this.dateMax = dateMax;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TaskParamDto{" +
                "dateMin=" + dateMin +
                ", dateMax=" + dateMax +
                ", userName='" + userName + '\'' +
                '}';
    }
}
