package myapp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Table(uniqueConstraints= @UniqueConstraint(columnNames={"project", "category", "label", "codeOrigami"}))
@Entity
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Project project;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String label;

    private String codeOrigami;

    private LocalDateTime creation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreation() {
        return creation;
    }

    public void setCreation(LocalDateTime creation) {
        this.creation = creation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id != null ? id.equals(task.id) : task.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", project='" + project + '\'' +
                ", category='" + category + '\'' +
                ", label='" + label + '\'' +
                ", codeOrigami='" + codeOrigami + '\'' +
                ", creation=" + creation +
                '}';
    }
}
