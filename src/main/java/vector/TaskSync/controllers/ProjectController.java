package vector.TaskSync.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.Project;
import vector.TaskSync.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project createdProject = projectService.createProject(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping("/teams/{teamId}/projects")
    @PreAuthorize("hasAnyRole('USER', 'TEAM_LEAD')")
    public ResponseEntity<List<Project>> getProjectsByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(projectService.getProjectsByTeam(teamId));
    }


}