package vector.TaskSync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vector.TaskSync.models.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
}
