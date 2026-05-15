package service;

import model.Task;
import repository.TaskRepository;
import java.util.UUID;

public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public Task createTask(String title) {
        Task t = new Task(UUID.randomUUID().toString(), title, 1);
        repo.getAll().add(t);
        repo.save();
        return t;
    }

    public boolean updateTask(String id, String title) {
        Task t = repo.findById(id);
        if (t != null && !t.isDeleted()) {
            t.updateTitle(title);
            repo.save();
            return true;
        }
        return false;
    }

    public boolean deleteTask(String id) {
        Task t = repo.findById(id);
        if (t != null && !t.isDeleted()) {
            t.markDeleted();
            repo.save();
            return true;
        }
        return false;
    }
}
