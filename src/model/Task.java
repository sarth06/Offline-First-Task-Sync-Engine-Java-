package model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Task {
    private final String id;
    private String title;
    private long lastUpdated;
    private int version;
    private boolean deleted;
    private SyncStatus syncStatus;

    public Task(String id, String title, int version) {
        this.id = id;
        this.title = title == null ? "" : title.trim();
        this.version = version;
        this.lastUpdated = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING;
        this.deleted = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getVersion() { return version; }
    public long getLastUpdated() { return lastUpdated; }
    public boolean isDeleted() { return deleted; }
    public SyncStatus getSyncStatus() { return syncStatus; }

    public void updateTitle(String title) {
        this.title = title == null ? "" : title.trim();
        this.version++;
        this.lastUpdated = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING;
    }

    public void markDeleted() {
        this.deleted = true;
        this.version++;
        this.lastUpdated = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING;
    }

    public void restore() {
        this.deleted = false;
        this.version++;
        this.lastUpdated = System.currentTimeMillis();
        this.syncStatus = SyncStatus.PENDING;
    }

    public void markPending() {
        this.syncStatus = SyncStatus.PENDING;
    }

    public void markSynced() {
        this.syncStatus = SyncStatus.SYNCED;
    }

    @Override
    public String toString() {
        String encodedTitle = Base64.getEncoder().encodeToString(title.getBytes(StandardCharsets.UTF_8));
        return id + "|" + encodedTitle + "|" + version + "|" + lastUpdated + "|" + deleted + "|" + syncStatus;
    }

    public static Task fromString(String line) {
        String[] p = line.split("\\|");
        if (p.length < 6) {
            throw new IllegalArgumentException("Invalid task record");
        }

        String parsedTitle;
        try {
            parsedTitle = new String(Base64.getDecoder().decode(p[1]), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            parsedTitle = p[1];
        }

        Task t = new Task(p[0], parsedTitle, Integer.parseInt(p[2]));
        t.lastUpdated = Long.parseLong(p[3]);
        t.deleted = Boolean.parseBoolean(p[4]);
        t.syncStatus = SyncStatus.from(p[5]);
        return t;
    }
}
