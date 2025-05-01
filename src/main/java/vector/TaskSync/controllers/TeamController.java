package vector.TaskSync.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.Team;
import vector.TaskSync.services.TeamService;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @PostMapping
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team newTeam = teamService.save(team);
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','TEAM_LEAD')")
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.findAllTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return teamService.findTeamById(id)
                .map(team -> new ResponseEntity<Team>(team,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam (@PathVariable Long id,@RequestBody Team team ) {
        try {
            Team updatedTeam = teamService.updateTeam(id, team);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Team> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{teamId}/members/{userId}")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Team> addMember(@PathVariable Long teamId, @PathVariable Long userId) {
        Team updatedTeam = teamService.addMember(teamId, userId);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}/members/{userId}")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Team> removeMember(@PathVariable Long teamId, @PathVariable Long userId) {
        Team updatedTeam = teamService.removeMember(teamId, userId);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }
}
