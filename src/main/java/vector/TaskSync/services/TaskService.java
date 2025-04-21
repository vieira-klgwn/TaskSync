package vector.TaskSync.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vector.TaskSync.models.Task;
import vector.TaskSync.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

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



}
