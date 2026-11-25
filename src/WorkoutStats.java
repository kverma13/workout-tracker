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

}
