import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutEntryTest {

    @Test
    void gettersReturnConstructorValues() {
        LocalDate date = LocalDate.of(2026, 11, 2);
        WorkoutEntry entry = new WorkoutEntry(date, "Bench Press", 3, 8, 135.0);

        assertEquals(date, entry.getDate());
        assertEquals("Bench Press", entry.getExercise());
        assertEquals(3, entry.getSets());
        assertEquals(8, entry.getReps());
        assertEquals(135.0, entry.getWeight(), 0.001);
    }

    @Test
    void toStringIsFormattedCorrectly() {
        WorkoutEntry entry = new WorkoutEntry(LocalDate.of(2026, 11, 2), "Squat", 3, 5, 185.0);

        assertEquals("2026-11-02 | Squat | 3x5 @ 185.0 lbs", entry.toString());
    }
}