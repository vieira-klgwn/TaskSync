package vector.TaskSync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vector.TaskSync.models.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTeamId(Long teamId);
//    Project findById(long id);

}
