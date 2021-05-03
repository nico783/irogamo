package myapp.repository;

import myapp.entity.IrogamoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<IrogamoUser, Long> {
    Optional<IrogamoUser> findByUsername(String username);
}
