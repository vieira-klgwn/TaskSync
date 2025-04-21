package vector.TaskSync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vector.TaskSync.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
    User findByEmail(String email);

}
