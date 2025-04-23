package vector.TaskSync.services;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vector.TaskSync.repositories.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamAccessService {
    private final TeamRepository teamRepository;

    public boolean isUserInTeam(Long teamId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return teamRepository.findById(teamId)
                .map(team -> team.getMembers().stream()
                        .anyMatch(user -> user.getEmail().equals(email)))
                .orElse(false);
    }
}
