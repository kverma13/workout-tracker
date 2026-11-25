import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class Main {
    private static ArrayList<WorkoutEntry> entries = WorkoutStorage.load();

    public static void main(String[] args) {
        boolean run = true;
        do {
            printMenu();
            int input;
            try {
                input = Integer.parseInt(IO.readln("CHOOSE AN OPTION: ").trim());
            } catch (NumberFormatException e) {
                IO.println("Enter a valid option");
                continue;
            }
            switch (input) {
                case 1:
                    logWorkout();
                    break;
                case 2:
                    viewHistory();
                    break;
                case 3:
                    //edit workout
                    editWorkout();
                    break;
                case 4:
                    deleteWorkout();
                    break;
                case 5:
                    run = false;
                    IO.println("GOODBYE");
                    IO.println("");
                    IO.println("THANK YOU FOR USING WORKOUT TRACKER");
                    break;
                default:
                    IO.println("Enter a valid option");
                    break;
            }
        } while (run);
    }

    private static void printMenu() {
        IO.println();
        IO.println("======WORKOUT TRACKER======");
        IO.println("1. Log a workout");
        IO.println("2. View history");
        IO.println("3. Edit entry");
        IO.println("4. Delete entry");
        IO.println("5. Quit");
    }

    //FEATURES

    private static void logWorkout() {
        String exercise = readText("Exercise: ");
        int sets = readPositiveInt("Sets: ");
        int reps = readPositiveInt("Reps: ");
        double weight = readPositiveDouble("Weight (lbs): ");

        WorkoutEntry entry = new WorkoutEntry(LocalDate.now(), exercise, sets, reps, weight);
        entries.add(entry);
        WorkoutStorage.save(entries);

        IO.println("Logged: " + entry);
        IO.println("Total entries: " + entries.size());
    }

    static String normalize(String exercise) {
        return exercise.toLowerCase(Locale.ROOT).trim();
    }

    private static void viewHistory() {
        if (entries.isEmpty()) {
            IO.println("No workouts logged yet. Choose option 1 to add one.");
            return;
        }

        IO.println();
        IO.println("=== Workout History ===");
        for (int i = 0; i < entries.size(); i++) {
            WorkoutEntry e = entries.get(i);

            if (WorkoutStats.isPersonalRecord(entries, e)) {
                IO.println((i + 1) + ")   " + e + " ***");
            } else {
                IO.println((i + 1) + ")   " + e);
            }
        }
        IO.println("*** = personal record");
        IO.println("Total entries: " + entries.size());
    }

    private static void editWorkout() {
        if (entries.isEmpty()) {
            IO.println("No workouts logged yet. Choose option 1 to add one.");
            return;
        }

        viewHistory();
        try {
            int option = Integer.parseInt(IO.readln("CHOOSE AN ENTRY TO EDIT: "));
            String exercise = readText("Exercise: ");
            int sets = readPositiveInt("Sets: ");
            int reps = readPositiveInt("Reps: ");
            double weight = readPositiveDouble("Weight (lbs): ");
            WorkoutEntry entry = new WorkoutEntry(entries.get(option-1).getDate(), exercise, sets, reps, weight);
            entries.set((option-1), entry);
            WorkoutStorage.save(entries);
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            IO.println("Enter a valid option");
        }

    }

    private static void deleteWorkout() {
        if (entries.isEmpty()) {
            IO.println("No workouts logged yet. Choose option 1 to add one.");
            return;
        }

        viewHistory();
        try {
            int option = Integer.parseInt(IO.readln("CHOOSE AN ENTRY TO DELETE: "));
            entries.remove((option-1));
            WorkoutStorage.save(entries);
        } catch(IndexOutOfBoundsException | NumberFormatException e) {
            IO.println("Enter a valid option");
        }

    }

    //CHECKS FOR INPUT HANDLING

    private static String readText(String prompt) {
        while (true) {
            String input = IO.readln(prompt).trim();
            if (!input.isEmpty()) {
                return input;
            }
            IO.println("Please enter something.");
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            try {
                int value = Integer.parseInt(IO.readln(prompt).trim());
                if (value > 0) {
                    return value;
                }
                IO.println("Enter a number greater than 0.");
            } catch (NumberFormatException e) {
                IO.println("Enter a whole number.");
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
                IO.println("Enter a number greater than 0.");
            } catch (NumberFormatException e) {
                IO.println("Enter a valid number.");
            }
        }
    }
}