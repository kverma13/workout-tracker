import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkoutStatsTest {

    private WorkoutEntry entry(String date, String exercise, int sets, int reps, double weight) {
        return new WorkoutEntry(LocalDate.parse(date), exercise, sets, reps, weight);
    }

    // ----- personal records -----

    @Test
    void heaviestEntryIsPersonalRecord() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        WorkoutEntry light = entry("2026-11-02", "Bench Press", 3, 8, 135);
        WorkoutEntry heavy = entry("2026-11-09", "Bench Press", 3, 8, 155);
        list.add(light);
        list.add(heavy);

        assertTrue(WorkoutStats.isPersonalRecord(list, heavy));
        assertFalse(WorkoutStats.isPersonalRecord(list, light));
    }

    @Test
    void personalRecordsAreTrackedPerExercise() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        WorkoutEntry bench = entry("2026-11-02", "Bench Press", 3, 8, 135);
        WorkoutEntry squat = entry("2026-11-02", "Squat", 3, 5, 185);
        list.add(bench);
        list.add(squat);

        assertTrue(WorkoutStats.isPersonalRecord(list, bench));
        assertTrue(WorkoutStats.isPersonalRecord(list, squat));
    }

    @Test
    void exerciseNamesIgnoreCaseAndSpaces() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        WorkoutEntry lower = entry("2026-11-02", "bench press ", 3, 8, 145);
        WorkoutEntry upper = entry("2026-11-09", "Bench Press", 3, 8, 135);
        list.add(lower);
        list.add(upper);

        assertTrue(WorkoutStats.isPersonalRecord(list, lower));
        assertFalse(WorkoutStats.isPersonalRecord(list, upper));
    }

    @Test
    void tiedBestWeightsAreBothPersonalRecords() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        WorkoutEntry first = entry("2026-11-02", "Deadlift", 1, 5, 225);
        WorkoutEntry second = entry("2026-11-09", "Deadlift", 1, 5, 225);
        list.add(first);
        list.add(second);

        assertTrue(WorkoutStats.isPersonalRecord(list, first));
        assertTrue(WorkoutStats.isPersonalRecord(list, second));
    }

    // ----- week start -----

    @Test
    void weekStartOfMondayIsItself() {
        assertEquals(LocalDate.of(2026, 11, 2), WorkoutStats.weekStart(LocalDate.of(2026, 11, 2)));
    }

    @Test
    void weekStartOfWednesdayIsThatMonday() {
        assertEquals(LocalDate.of(2026, 11, 2), WorkoutStats.weekStart(LocalDate.of(2026, 11, 4)));
    }

    @Test
    void weekStartOfSundayIsTheMondayBefore() {
        assertEquals(LocalDate.of(2026, 11, 2), WorkoutStats.weekStart(LocalDate.of(2026, 11, 8)));
    }

    // ----- weekly volume -----

    @Test
    void volumeAddsSetsTimesRepsTimesWeekForOneWeek() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        list.add(entry("2026-11-02", "Bench Press", 3, 8, 135));
        list.add(entry("2026-11-04", "Squat", 3, 5, 185));

        double total = WorkoutStats.volumeForWeek(list, LocalDate.of(2026, 11, 2));

        assertEquals(6015.0, total, 0.001);
    }

    @Test
    void volumeOnlyCountsEntriesInThatWeek() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        list.add(entry("2026-11-02", "Bench Press", 3, 8, 135));
        list.add(entry("2026-11-16", "Bench Press", 3, 8, 145));

        assertEquals(3240.0, WorkoutStats.volumeForWeek(list, LocalDate.of(2026, 11, 2)), 0.001);
        assertEquals(3480.0, WorkoutStats.volumeForWeek(list, LocalDate.of(2026, 11, 16)), 0.001);
    }

    @Test
    void weekWithNoWorkoutsHasZeroVolume() {
        ArrayList<WorkoutEntry> list = new ArrayList<>();
        list.add(entry("2026-11-02", "Bench Press", 3, 8, 135));

        assertEquals(0.0, WorkoutStats.volumeForWeek(list, LocalDate.of(2026, 11, 9)), 0.001);
    }
}