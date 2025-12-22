package app;

import repository.TaskRepository;
import service.TaskService;
import service.SyncService;
import model.Task;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static void printMenu() {
        System.out.println("\n=================================");
        System.out.println(" OFFLINE TASK SYNC ENGINE ");
        System.out.println("=================================");
        System.out.println("1. Add Task");
        System.out.println("2. Update Task");
        System.out.println("3. Delete Task");
        System.out.println("4. View All Tasks");
        System.out.println("5. Sync Tasks");
        System.out.println("6. Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {

        TaskRepository repo = new TaskRepository();
        TaskService service = new TaskService(repo);
        SyncService syncService = new SyncService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter task title: ");
                    String title = sc.nextLine();
                    service.createTask(title);
                    System.out.println("Task added successfully.");
                    break;

                case "2":
                    System.out.print("Enter task ID to update: ");
                    String uid = sc.nextLine();
                    System.out.print("Enter new title: ");
                    String newTitle = sc.nextLine();
                    service.updateTask(uid, newTitle);
                    System.out.println("Task updated (if ID existed).");
                    break;

                case "3":
                    System.out.print("Enter task ID to delete: ");
                    String did = sc.nextLine();
                    service.deleteTask(did);
                    System.out.println("Task deleted (if ID existed).");
                    break;

                case "4":
                    List<Task> tasks = repo.getAll();
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks available.");
                    } else {
                        System.out.println("\n--- TASK LIST ---");
                        for (Task t : tasks) {
                            System.out.println("ID: " + t.getId());
                            System.out.println("Title: " + t.getTitle());
                            System.out.println("Version: " + t.getVersion());
                            System.out.println("Status: " + t.getSyncStatus());
                            System.out.println("---------------------");
                        }
                    }
                    break;

                case "5":
                    syncService.sync(repo);
                    break;

                case "6":
                    System.out.println("Exiting application. Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
