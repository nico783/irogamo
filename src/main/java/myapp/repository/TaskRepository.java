package myapp.repository;

import myapp.entity.Category;
import myapp.entity.Project;
import myapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Set<Task> findByCategoryAndProjectAndLabel(Category category, Project project, String label);

}
