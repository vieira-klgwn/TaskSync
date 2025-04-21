package vector.TaskSync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vector.TaskSync.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
