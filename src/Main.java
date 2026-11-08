import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        WorkoutEntry entry = new WorkoutEntry(LocalDate.now(), "Bench Press", 3, 8, 135);
        System.out.println(entry);
    }
}