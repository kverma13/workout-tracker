import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    private static ArrayList<WorkoutEntry> entries = WorkoutStorage.load();

    private static final String LINE = "=".repeat(60);
    private static final String THIN = "-".repeat(60);
    private static final String ROW = "  %-4s %-11s %-18s %-10s %-10s %s";

    public static void main(String[] args) {
        printBanner();
        boolean run = true;

        while (run) {
            printMenu();
            String choice = IO.readln("  Choose an option: ").trim();

            switch (choice) {
                case "1" -> { logWorkout(); pause(); }
                case "2" -> { viewHistory(); pause(); }
                case "3" -> { filterByExercise(); pause(); }
                case "4" -> { editWorkout(); pause(); }
                case "5" -> { deleteWorkout(); pause(); }
                case "6" -> {
                    run = false;
                    IO.println();
                    IO.println(LINE);
                    IO.println("  Goodbye! Thanks for using Workout Tracker.");
                    IO.println(LINE);
                }
                default -> IO.println("  Please enter a number from 1 to 6.");
            }
        }
    }

    // ---------- MENU AND LAYOUT ----------

    private static void printBanner() {
        IO.println();
        IO.println(LINE);
        IO.println("                    WORKOUT TRACKER");
        IO.println("          Log it. Track it. Beat your records.");
        IO.println(LINE);
        IO.println("  Loaded " + entries.size() + " saved workout(s).");
    }

    private static void printMenu() {
        IO.println();
        IO.println(THIN);
        IO.println("  MAIN MENU");
        IO.println(THIN);
        IO.println("  [1]  Log a workout");
        IO.println("  [2]  View history");
        IO.println("  [3]  Filter by exercise");
        IO.println("  [4]  Edit an entry");
        IO.println("  [5]  Delete an entry");
        IO.println("  [6]  Quit");
        IO.println(THIN);
    }

    private static void printHeader(String title) {
        IO.println();
        IO.println(LINE);
        IO.println("  " + title);
        IO.println(LINE);
    }

    private static void printTableHeader() {
        IO.println(String.format(ROW, "#", "DATE", "EXERCISE", "SETS/REPS", "WEIGHT", ""));
        IO.println(THIN);
    }

    private static void printRow(int number, WorkoutEntry e, boolean isPr) {
        IO.println(String.format(ROW,
                number + ")",
                e.getDate(),
                shorten(e.getExercise(), 18),
                e.getSets() + " x " + e.getReps(),
                formatWeight(e.getWeight()) + " lbs",
                isPr ? "PR" : ""));
    }

    private static void pause() {
        IO.readln("\n  Press Enter to return to the menu...");
    }

    private static String formatWeight(double weight) {
        if (weight == Math.floor(weight)) {
            return String.valueOf((long) weight);
        }
        return String.valueOf(weight);
    }

    private static String shorten(String text, int max) {
        if (text.length() <= max) {
            return text;
        }
        return text.substring(0, max - 3) + "...";
    }

    static String normalize(String exercise) {
        return exercise.toLowerCase(Locale.ROOT).trim();
    }

    // ---------- FEATURES ----------

    private static void logWorkout() {
        printHeader("LOG A WORKOUT");

        String exercise = readText("  Exercise: ");
        int sets = readPositiveInt("  Sets: ");
        int reps = readPositiveInt("  Reps: ");
        double weight = readPositiveDouble("  Weight (lbs): ");

        WorkoutEntry entry = new WorkoutEntry(LocalDate.now(), exercise, sets, reps, weight);
        entries.add(entry);
        WorkoutStorage.save(entries);

        IO.println();
        IO.println("  Saved! Entry #" + entries.size() + ":");
        IO.println(THIN);
        printRow(entries.size(), entry, WorkoutStats.isPersonalRecord(entries, entry));
    }

    private static void viewHistory() {
        printHeader("WORKOUT HISTORY");

        if (entries.isEmpty()) {
            IO.println("  No workouts logged yet. Choose option 1 to add one.");
            return;
        }

        printTableHeader();
        for (int i = 0; i < entries.size(); i++) {
            WorkoutEntry e = entries.get(i);
            printRow(i + 1, e, WorkoutStats.isPersonalRecord(entries, e));
        }
        IO.println(THIN);
        IO.println("  PR = personal record      Total entries: " + entries.size());
    }

    private static void filterByExercise() {
        printHeader("FILTER BY EXERCISE");

        if (entries.isEmpty()) {
            IO.println("  No workouts logged yet. Choose option 1 to add one.");
            return;
        }

        String search = normalize(readText("  Exercise to search for: "));
        int matches = 0;

        IO.println();
        IO.println("  Results for \"" + search + "\":");
        IO.println(THIN);
        printTableHeader();

        for (int i = 0; i < entries.size(); i++) {
            WorkoutEntry e = entries.get(i);
            if (normalize(e.getExercise()).contains(search)) {
                printRow(i + 1, e, WorkoutStats.isPersonalRecord(entries, e));
                matches++;
            }
        }

        IO.println(THIN);
        if (matches == 0) {
            IO.println("  No workouts found for \"" + search + "\".");
        } else {
            IO.println("  PR = personal record      Matches: " + matches);
        }
    }

    private static void editWorkout() {
        viewHistory();
        if (entries.isEmpty()) {
            return;
        }

        IO.println();
        int index = readEntryNumber("  Entry # to edit (Enter to cancel): ");
        if (index < 0) {
            IO.println("  Edit cancelled.");
            return;
        }

        WorkoutEntry old = entries.get(index);
        IO.println();
        IO.println("  Press Enter to keep the current value.");

        String exercise = readTextOrKeep("  Exercise", old.getExercise());
        int sets = readIntOrKeep("  Sets", old.getSets());
        int reps = readIntOrKeep("  Reps", old.getReps());
        double weight = readDoubleOrKeep("  Weight (lbs)", old.getWeight());

        WorkoutEntry updated = new WorkoutEntry(old.getDate(), exercise, sets, reps, weight);
        entries.set(index, updated);
        WorkoutStorage.save(entries);

        IO.println();
        IO.println("  Updated! Entry #" + (index + 1) + ":");
        IO.println(THIN);
        printRow(index + 1, updated, WorkoutStats.isPersonalRecord(entries, updated));
    }

    private static void deleteWorkout() {
        viewHistory();
        if (entries.isEmpty()) {
            return;
        }

        IO.println();
        int index = readEntryNumber("  Entry # to delete (Enter to cancel): ");
        if (index < 0) {
            IO.println("  Delete cancelled.");
            return;
        }

        WorkoutEntry target = entries.get(index);
        IO.println();
        printRow(index + 1, target, false);
        String confirm = IO.readln("  Delete this entry? (y/n): ").trim().toLowerCase(Locale.ROOT);

        if (confirm.equals("y") || confirm.equals("yes")) {
            entries.remove(index);
            WorkoutStorage.save(entries);
            IO.println("  Deleted. " + entries.size() + " entries remaining.");
        } else {
            IO.println("  Delete cancelled. Nothing was changed.");
        }
    }

    // ---------- INPUT HANDLING ----------

    private static int readEntryNumber(String prompt) {
        while (true) {
            String input = IO.readln(prompt).trim();
            if (input.isEmpty()) {
                return -1;
            }
            try {
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= entries.size()) {
                    return number - 1;
                }
                IO.println("  Pick a number between 1 and " + entries.size() + ".");
            } catch (NumberFormatException e) {
                IO.println("  Enter a whole number.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            String input = IO.readln(prompt).trim();
            if (!input.isEmpty()) {
                return input;
            }
            IO.println("  Please enter something.");
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            try {
                int value = Integer.parseInt(IO.readln(prompt).trim());
                if (value > 0) {
                    return value;
                }
                IO.println("  Enter a number greater than 0.");
            } catch (NumberFormatException e) {
                IO.println("  Enter a whole number.");
            }
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(IO.readln(prompt).trim());
                if (value > 0) {
                    return value;
                }
                IO.println("  Enter a number greater than 0.");
            } catch (NumberFormatException e) {
                IO.println("  Enter a valid number.");
            }
        }
    }

    private static String readTextOrKeep(String label, String current) {
        String input = IO.readln(label + " [" + current + "]: ").trim();
        return input.isEmpty() ? current : input;
    }

    private static int readIntOrKeep(String label, int current) {
        while (true) {
            String input = IO.readln(label + " [" + current + "]: ").trim();
            if (input.isEmpty()) {
                return current;
            }
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                IO.println("  Enter a number greater than 0.");
            } catch (NumberFormatException e) {
                IO.println("  Enter a whole number.");
            }
        }
    }

    private static double readDoubleOrKeep(String label, double current) {
        while (true) {
            String input = IO.readln(label + " [" + formatWeight(current) + "]: ").trim();
            if (input.isEmpty()) {
                return current;
            }
            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
                IO.println("  Enter a number greater than 0.");
            } catch (NumberFormatException e) {
                IO.println("  Enter a valid number.");
            }
        }
    }
}