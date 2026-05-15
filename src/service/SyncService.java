package service;

import model.Task;
import model.SyncStatus;
import repository.TaskRepository;

public class SyncService {

    public int sync(TaskRepository repo) {
        int synced = 0;
        for (Task t : repo.getAll()) {
            if (SyncStatus.PENDING.equals(t.getSyncStatus())) {
                t.markSynced();
                synced++;
            }
        }
        repo.save();
        return synced;
    }
}
