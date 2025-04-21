package vector.TaskSync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vector.TaskSync.models.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);

}
