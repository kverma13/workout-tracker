import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

public class WorkoutStats {

    public static boolean isPersonalRecord(ArrayList<WorkoutEntry> entries, WorkoutEntry entry) {
        String name = Main.normalize(entry.getExercise());

        for (WorkoutEntry other : entries) {
            if (Main.normalize(other.getExercise()).equals(name)
                    && other.getWeight() > entry.getWeight()) {
                return false;
            }
        }
        return true;
    }

    public static LocalDate weekStart(LocalDate date) {
        return date.with(DayOfWeek.MONDAY);
    }

    public static double volumeForWeek(ArrayList<WorkoutEntry> entries, LocalDate weekStart) {
        double total = 0;
        for (WorkoutEntry e : entries) {
            if (weekStart(e.getDate()).equals(weekStart)) {
                total += e.getSets() * e.getReps() * e.getWeight();
            }
        }
        return total;
    }

}
