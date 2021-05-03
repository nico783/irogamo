package myapp.featuretask;

import myapp.entity.Category;
import myapp.entity.Project;

public class TaskDto {

    private Project project;

    private Category category;

    private String label;

    private String codeOrigami;

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

    public String getCodeOrigami() {
        return codeOrigami;
    }

    public void setCodeOrigami(String codeOrigami) {
        this.codeOrigami = codeOrigami;
    }

    @Override
    public String toString() {
        return "TaskDto{" +
                "project='" + project + '\'' +
                ", category='" + category + '\'' +
                ", label='" + label + '\'' +
                ", codeOrigami='" + codeOrigami + '\'' +
                '}';
    }
}
