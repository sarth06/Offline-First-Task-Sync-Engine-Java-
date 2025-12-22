package repository;

import model.Task;
import util.FileUtil;
import java.util.*;

public class TaskRepository {
    private List<Task> tasks;

    public TaskRepository() {
        tasks = FileUtil.load();
    }

    public List<Task> getAll() {
        return tasks;
    }

    public Task findById(String id) {
        for (Task t : tasks) {
            if (t.getId().equals(id)) return t;
        }
        return null;
    }

    public void save() {
        FileUtil.save(tasks);
    }
}
