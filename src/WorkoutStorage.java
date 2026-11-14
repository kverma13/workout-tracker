import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class WorkoutStorage {
    private static final String FILE_PATH = "data/workouts.csv";
    private static final String HEADER = "date,exercise,sets,reps,weight";

    public static void save(List<WorkoutEntry> entries)  {
        File file = new File(FILE_PATH);
        File folder = file.getParentFile();
        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(HEADER);
            for (WorkoutEntry e : entries) {
                writer.println(e.getDate() + "," + e.getExercise() + "," + e.getSets()
                        + "," + e.getReps() + "," + e.getWeight());
            }
        } catch (IOException ex) {
            System.out.println("Could not save workouts: " + ex.getMessage());
        }

    }

    public static ArrayList<WorkoutEntry> load() {
        ArrayList<WorkoutEntry> entries = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return entries;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();

            while((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                entries.add(new WorkoutEntry(LocalDate.parse(parts[0]),
                        parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4])));
            }

        } catch (IOException ex) {
            System.out.println("Could not load workouts: " + ex.getMessage());
        }

        return entries;
    }


}
