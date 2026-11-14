import java.time.LocalDate;
import java.lang.IO;
import java.util.ArrayList;

public class Main {
    private static ArrayList<WorkoutEntry> entries = WorkoutStorage.load();

    public static void main(String[] args) {
        WorkoutEntry entry = new WorkoutEntry(LocalDate.now(), "Bench Press", 3, 8, 135);
        IO.println(entry);

        boolean run = true;
        do {
            printMenu();
            int input = Integer.parseInt(IO.readln(""));
            switch(input) {
                case 1:
                    logWorkout();
                    break;
                case 2:
                    IO.println("======LOG HISTORY======");
                    for (int i = 0; i < entries.size(); i++) {
                        IO.println(entries.get(i));
                    }
                    break;
                case 3:
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
        IO.println("3. Quit");
        IO.println("CHOOSE AN OPTION: ");


    }

    private static void logWorkout() {
        String exersize = IO.readln("Exersize: ");
        int sets = Integer.parseInt(IO.readln("Sets: "));
        int reps = Integer.parseInt(IO.readln("Reps: "));
        double weight = Double.parseDouble(IO.readln("Weight(lbs): "));

        WorkoutEntry entry = new WorkoutEntry(LocalDate.now(), exersize, sets, reps, weight);
        entries.add(entry);
        IO.println("Logged: " + entry);
        IO.println("Total entries: " + entries.size());
        WorkoutStorage.save(entries);

    }


}