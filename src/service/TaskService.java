package service;

import model.Task;
import repository.TaskRepository;
import java.util.UUID;

public class TaskService {
    private TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public void createTask(String title) {
        Task t = new Task(UUID.randomUUID().toString(), title, 1);
        repo.getAll().add(t);
        repo.save();
    }

    public void updateTask(String id, String title) {
        Task t = repo.findById(id);
        if (t != null) {
            t.updateTitle(title);
            repo.save();
        }
    }

    public void deleteTask(String id) {
        Task t = repo.findById(id);
        if (t != null) {
            t.markDeleted();
            repo.save();
        }
    }
}
