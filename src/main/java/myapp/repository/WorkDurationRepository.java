package myapp.repository;

import myapp.entity.WorkDuration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkDurationRepository extends JpaRepository<WorkDuration, Long> {
}
