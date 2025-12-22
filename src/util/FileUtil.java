package util;

import model.Task;
import java.io.*;
import java.util.*;

public class FileUtil {

    private static final String FILE =
            new File("data/tasks.db").getAbsolutePath();

    public static void save(List<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Task t : tasks) {
                bw.write(t.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    public static List<Task> load() {
        List<Task> tasks = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return tasks;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                tasks.add(Task.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks.");
        }

        return tasks;
    }
}
