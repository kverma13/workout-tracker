import java.time.LocalDate;
import java.lang.IO;

public class Main {
    public static void main(String[] args) {
        WorkoutEntry entry = new WorkoutEntry(LocalDate.now(), "Bench Press", 3, 8, 135);
        IO.println(entry);

        boolean run = true;
        do {
            printMenu();
            int input = Integer.parseInt(IO.readln(""));
            switch(input) {
                case 1:
                    IO.println("LOGGING COMING SOON");
                    break;
                case 2:
                    IO.println("VIEW HISTORY COMING SOON");
                    break;
                case 3:
                    run = false;
                    IO.println("GOODBYE");
                    IO.println("=====THANK YOU FOR USING WORKOUT TRACKER=====");
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


}