package model;

public class Task {
    private String id;
    private String title;
    private long lastUpdated;
    private int version;
    private boolean deleted;
    private String syncStatus;

    public Task(String id, String title, int version) {
        this.id = id;
        this.title = title;
        this.version = version;
        this.lastUpdated = System.currentTimeMillis();
        this.syncStatus = "PENDING";
        this.deleted = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getVersion() { return version; }
    public long getLastUpdated() { return lastUpdated; }
    public boolean isDeleted() { return deleted; }
    public String getSyncStatus() { return syncStatus; }

    public void updateTitle(String title) {
        this.title = title;
        this.version++;
        this.lastUpdated = System.currentTimeMillis();
        this.syncStatus = "PENDING";
    }

    public void markDeleted() {
        this.deleted = true;
        this.version++;
        this.syncStatus = "PENDING";
    }

    public void markSynced() {
        this.syncStatus = "SYNCED";
    }

    @Override
    public String toString() {
        return id + "|" + title + "|" + version + "|" + lastUpdated + "|" + deleted + "|" + syncStatus;
    }

    public static Task fromString(String line) {
        String[] p = line.split("\\|");
        Task t = new Task(p[0], p[1], Integer.parseInt(p[2]));
        t.lastUpdated = Long.parseLong(p[3]);
        t.deleted = Boolean.parseBoolean(p[4]);
        t.syncStatus = p[5];
        return t;
    }
}
