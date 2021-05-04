package myapp.featurework;

import myapp.entity.BusinessUnit;
import myapp.entity.Category;
import myapp.entity.Project;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class WorkDoneDto {

    private Project project;

    private Category category;

    private String label;

    private BusinessUnit businessUnit;

    private String description;

    private Integer duration;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate creation;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public LocalDate getCreation() {
        return creation;
    }

    public void setCreation(LocalDate creation) {
        this.creation = creation;
    }

    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    @Override
    public String toString() {
        return "WorkDoneDto{" +
                "project=" + project +
                ", category=" + category +
                ", label='" + label + '\'' +
                ", businessUnit=" + businessUnit +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", creation=" + creation +
                '}';
    }
}
