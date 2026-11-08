import java.time.LocalDate;

public class WorkoutEntry {
    private LocalDate date;
    private String exercise;
    private int sets;
    private int reps;
    private double weight;

    public WorkoutEntry(LocalDate date, String exercise, int sets, int reps, double weight) {
        this.date = date;
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getExercise() {
        return exercise;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return date + " | " + exercise + " | " + sets + "x" + reps + " @ " + weight + " lbs";
    }
}