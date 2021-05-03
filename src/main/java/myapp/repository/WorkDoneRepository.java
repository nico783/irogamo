package myapp.repository;

import myapp.entity.IrogamoUser;
import myapp.entity.Task;
import myapp.entity.WorkDone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface WorkDoneRepository extends JpaRepository<WorkDone, Long> {

    Optional<WorkDone> findByIrogamoUserAndTask(IrogamoUser user, Task task);

    Set<WorkDone> findByIrogamoUser(IrogamoUser irogamoUser);

}
