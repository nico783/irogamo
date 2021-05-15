package myapp.featurestatistic;

import myapp.entity.BusinessUnit;
import myapp.entity.Category;
import myapp.entity.Project;

import java.time.LocalDate;

public class StatisticDto {

    private String username;
    private LocalDate date;
    private Category category;
    private Project project;
    private String label;
    private BusinessUnit bu;
    private String codeOrigami;
    private String description;
    private Integer duration;

    public StatisticDto(String username, LocalDate date, Category category, Project project, String label, BusinessUnit bu, String codeOrigami, String description, Integer duration) {
        this.username = username;
        this.date = date;
        this.category = category;
        this.project = project;
        this.label = label;
        this.bu = bu;
        this.codeOrigami = codeOrigami;
        this.description = description;
        this.duration = duration;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public BusinessUnit getBu() {
        return bu;
    }

    public void setBu(BusinessUnit bu) {
        this.bu = bu;
    }

    public String getCodeOrigami() {
        return codeOrigami;
    }

    public void setCodeOrigami(String codeOrigami) {
        this.codeOrigami = codeOrigami;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "StatisticDto{" +
                "username='" + username + '\'' +
                ", date=" + date +
                ", category=" + category +
                ", project=" + project +
                ", label='" + label + '\'' +
                ", bu=" + bu +
                ", codeOrigami='" + codeOrigami + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                '}';
    }
}
