package myapp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class WorkDone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private IrogamoUser irogamoUser;

    @ManyToOne
    private Task task;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<WorkDuration> durations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IrogamoUser getIrogamoUser() {
        return irogamoUser;
    }

    public void setIrogamoUser(IrogamoUser irogamoUser) {
        this.irogamoUser = irogamoUser;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Set<WorkDuration> getDurations() {
        return durations;
    }

    public void setDurations(Set<WorkDuration> durations) {
        this.durations = durations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkDone workDone = (WorkDone) o;

        return id != null ? id.equals(workDone.id) : workDone.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "WorkDone{" +
                "id=" + id +
                ", irogamoUser=" + irogamoUser +
                ", task=" + task +
                ", durations=" + durations +
                '}';
    }
}
