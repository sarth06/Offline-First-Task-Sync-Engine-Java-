package util;

import model.Task;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUtil {

    private static final Path FILE = Paths.get(
            System.getProperty("user.dir"),
            "data",
            "tasks.db"
    ).toAbsolutePath().normalize();

    public static void save(List<Task> tasks) {
        try {
            Path parent = FILE.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            System.out.println("Error preparing task storage path: " + FILE);
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE.toFile()))) {
            for (Task t : tasks) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks to: " + FILE);
        }
    }

    public static List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File f = FILE.toFile();
        if (!f.exists()) return tasks;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    tasks.add(Task.fromString(line));
                } catch (RuntimeException ignored) {
                    System.out.println("Skipped invalid task record.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks from: " + FILE);
        }

        return tasks;
    }
}
