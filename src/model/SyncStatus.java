package model;

public enum SyncStatus {
    PENDING,
    SYNCED;

    public static SyncStatus from(String raw) {
        if (raw == null) {
            return PENDING;
        }

        try {
            return SyncStatus.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return PENDING;
        }
    }
}
