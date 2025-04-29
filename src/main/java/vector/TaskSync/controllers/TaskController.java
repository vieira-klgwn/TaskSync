package vector.TaskSync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.Task;
import vector.TaskSync.services.TaskService;
import vector.TaskSync.services.TeamAccessService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TeamAccessService teamAccessService;

    @PostMapping("/projects/{projectId}/tasks")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Task> createTask(@PathVariable Long projectId, @Valid @RequestBody Task task) {
        Task createdTask = taskService.createTask(projectId, task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }



    @GetMapping("/projects/{projectId}/tasks")
    @PreAuthorize("hasAnyRole('USER', 'TEAM_LEAD')")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId));
    }

    @GetMapping("/tasks/{id}")
    @PreAuthorize("@teamAccessService.isUserInProject(#id)")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/tasks/{id}")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tasks/assign/{taskId}/{userId}")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        Task assignedTask = taskService.assignTask(taskId, userId);
        return new ResponseEntity<>(assignedTask, HttpStatus.OK);
    }
}