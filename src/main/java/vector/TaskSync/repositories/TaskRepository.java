package vector.TaskSync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vector.TaskSync.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> getTasksByProjectId(Long projectId);

    List<Task> findByProjectId(Long id);
}
