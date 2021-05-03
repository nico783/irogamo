package myapp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
public class WorkDuration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BusinessUnit businessUnit;

    private String description;

    private Integer duration;

    private LocalDate creation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkDuration that = (WorkDuration) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkDuration{" +
                "id=" + id +
                ", businessUnit=" + businessUnit +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", creation=" + creation +
                '}';
    }
}
