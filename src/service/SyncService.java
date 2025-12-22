package service;

import model.Task;
import repository.TaskRepository;

public class SyncService {

    public void sync(TaskRepository repo) {
        for (Task t : repo.getAll()) {
            if ("PENDING".equals(t.getSyncStatus())) {
                t.markSynced();
            }
        }
        repo.save();
        System.out.println("All pending tasks synced successfully.");
    }
}
