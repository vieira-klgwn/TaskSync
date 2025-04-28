package vector.TaskSync.services;

import com.sun.jdi.InvalidLineNumberException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vector.TaskSync.models.Task;
import vector.TaskSync.models.Team;
import vector.TaskSync.models.User;
import vector.TaskSync.repositories.TaskRepository;
import vector.TaskSync.repositories.TeamRepository;
import vector.TaskSync.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    private final TeamRepository teamRepository;

    //Create
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    //read all
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    //Read (by id)
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    //Update
    public Task updateTask(Long id, Task udatedTask) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if(existingTask.isPresent()){
            Task task = existingTask.get();
            task.setTitle(udatedTask.getTitle());
            task.setDescription(udatedTask.getDescription());
            task.setStatus(udatedTask.getStatus());
            task.setDueDate(udatedTask.getDueDate());
            task.setAssignee(udatedTask.getAssignee());
            task.setTeam(udatedTask.getTeam());

            return taskRepository.save(task);
        }
        throw new RuntimeException("Task not found with id: " +id);
    }

    //delete
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    //assign task

    public Task assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found with id: " +taskId));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        //Check if the user is among the task's team
        if (task.getTeam() != null && !task.getTeam().getMembers().contains(user)) {
            throw new IllegalStateException("User is not in the task's team");
        }
        task.setAssignee(user);
        return taskRepository.save(task);



    }







}
