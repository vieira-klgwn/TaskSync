package vector.TaskSync.controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.Task;
import vector.TaskSync.services.TaskService;
import vector.TaskSync.services.TeamAccessService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final TeamAccessService teamAccessService;


    //create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    //get all tasks
    @GetMapping
    @PreAuthorize("hasAnyRole('USER,TEAM_LEAD')")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks().stream()
                .filter(task ->  task.getTeam() == null || teamAccessService.isUserInTeam(task.getTeam().getId()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tasks,HttpStatus.OK);

    }


    @GetMapping("/{id}")
    @PreAuthorize("@teamAccessService.isUserInTeam(#id)")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id)
                 .map(task -> new ResponseEntity<>(task,HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> upadateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        try{
            Task updatedTask = taskService.updateTask(id, task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        }
        catch(RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        try{
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/assign/{taskId}/{userId}")
    @PreAuthorize("hasRole('TEAM_LEAD')")
    public ResponseEntity<Task> assignTaskToTeam(@PathVariable Long taskId, @PathVariable Long userId) {
        Task assignedTask = taskService.assignTask(taskId, userId);
        return new ResponseEntity<>(assignedTask,HttpStatus.OK);
    }

}
