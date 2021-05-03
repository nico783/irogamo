package myapp.dto;

import myapp.entity.BusinessUnit;
import myapp.entity.Category;
import myapp.entity.Project;
import myapp.entity.Task;

public class TaskResponseDto {

    private String userName;

    private Task task;

    private Project project;

    private Category category;

    private String label;

    private BusinessUnit businessUnit;

    private String origami;

    private Integer durationInMin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Project getProject() {
        return task.getProject();
    }

    public Category getCategory() {
        return task.getCategory();
    }

    public String getLabel() {
        return task.getLabel();
    }

    public String getOrigami() {
        return task.getCodeOrigami();
    }

    public Integer getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(Integer durationInMin) {
        this.durationInMin = durationInMin;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    @Override
    public String toString() {
        return "TaskResponseDto{" +
                "userName='" + userName + '\'' +
                ", project=" + project +
                ", category=" + category +
                ", label='" + label + '\'' +
                ", businessUnit=" + businessUnit +
                ", origami='" + origami + '\'' +
                ", durationInMin=" + durationInMin +
                '}';
    }
}
