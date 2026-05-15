# OfflineSyncEngine (Java)

## Overview
A professional offline-first task management system with persistent local storage and manual sync simulation.

## Features
- Professional menu-driven frontend (console UI) for full task lifecycle activities
- Offline task create, update, delete, and listing flows
- Persistent local file storage in `data/tasks.db`
- Sync state tracking with `PENDING` and `SYNCED` statuses
- Manual sync operation to simulate real-world data synchronization
- Clean layered architecture:
  - `app` → frontend/UI flow
  - `service` → business logic
  - `repository` → data access
  - `util` → file handling utilities
- Runtime-safe storage path handling and auto-creation of missing data directories

## How to Run
1. Open project root in IntelliJ
2. Mark `src` as Sources Root
3. Run `app/Main.java`
