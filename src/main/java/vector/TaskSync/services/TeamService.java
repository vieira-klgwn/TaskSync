package vector.TaskSync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vector.TaskSync.models.Team;
import vector.TaskSync.repositories.TeamRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;

    //create
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    //find all
    public List<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    //find by id
    public Optional<Team> findTeamById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    //update
    public Team updateTeam(Long id,Team team) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            Team updatedTeam = optionalTeam.get();
            updatedTeam.setName(team.getName());
            updatedTeam.setId(team.getId());
            updatedTeam.setMembers(team.getMembers());
            updatedTeam.setTasks(team.getTasks());

            return teamRepository.save(updatedTeam);

        }
        throw new RuntimeException("Team not found");
    }

    //delete
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
