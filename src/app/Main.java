package app;

import model.Task;
import repository.TaskRepository;
import service.TaskService;
import service.SyncService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int TITLE_DISPLAY_WIDTH = 29;

    private static void printMenu() {
        System.out.println("\n===================================================");
        System.out.println("            OFFLINE TASK SYNC ENGINE");
        System.out.println("===================================================");
        System.out.println("1. Add task");
        System.out.println("2. Update task");
        System.out.println("3. Delete task");
        System.out.println("4. View active tasks");
        System.out.println("5. View all tasks (including deleted)");
        System.out.println("6. Sync pending tasks");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty. Try again.");
        }
    }

    private static void printTasks(List<Task> tasks, boolean includeDeleted) {
        boolean hasRows = false;
        System.out.println("\n-----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-38s %-30s %-8s %-8s %-15s%n", "ID", "TITLE", "VERSION", "DELETED", "SYNC STATUS");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        for (Task t : tasks) {
            if (!includeDeleted && t.isDeleted()) {
                continue;
            }
            hasRows = true;
            System.out.printf("%-38s %-30s %-8d %-8s %-15s%n",
                    t.getId(),
                    shorten(t.getTitle(), TITLE_DISPLAY_WIDTH),
                    t.getVersion(),
                    t.isDeleted(),
                    t.getSyncStatus());
        }

        if (!hasRows) {
            System.out.println("No tasks available.");
        }
        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }

    private static String shorten(String value, int max) {
        if (value.length() <= max) {
            return value;
        }
        return value.substring(0, max - 3) + "...";
    }

    public static void main(String[] args) {
        TaskRepository repo = new TaskRepository();
        TaskService service = new TaskService(repo);
        SyncService syncService = new SyncService();

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                printMenu();
                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1":
                        String title = readNonEmpty(sc, "Enter task title: ");
                        Task created = service.createTask(title);
                        System.out.println("Task added successfully. ID: " + created.getId());
                        break;

                    case "2":
                        String uid = readNonEmpty(sc, "Enter task ID to update: ");
                        String newTitle = readNonEmpty(sc, "Enter new task title: ");
                        if (service.updateTask(uid, newTitle)) {
                            System.out.println("Task updated successfully.");
                        } else {
                            System.out.println("Task not found or already deleted.");
                        }
                        break;

                    case "3":
                        String did = readNonEmpty(sc, "Enter task ID to delete: ");
                        if (service.deleteTask(did)) {
                            System.out.println("Task deleted successfully.");
                        } else {
                            System.out.println("Task not found or already deleted.");
                        }
                        break;

                    case "4":
                        printTasks(repo.getAll(), false);
                        break;

                    case "5":
                        printTasks(repo.getAll(), true);
                        break;

                    case "6":
                        int syncedCount = syncService.sync(repo);
                        System.out.println("Sync complete. Tasks synced: " + syncedCount);
                        break;

                    case "7":
                        System.out.println("Exiting application. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
}
