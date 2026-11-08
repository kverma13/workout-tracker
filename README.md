# Workout Tracker

A command-line application written in Java for logging and reviewing weight training sessions. I built it because my workout notes were scattered and unstructured, which made it hard to tell whether I was actually progressing.

## Features (planned by MVP)

- **MVP 1:** Log workouts (exercise, sets, reps, weight, date), save them to a file, and view your full history
- **MVP 2:** Input validation, filtering by exercise or date, and personal records
- **MVP 3:** Weekly volume stats, edit/delete entries, and unit tests

## Tech Stack

- **Language:** Java (JDK 17+)
- **Libraries:** Java standard library only; JUnit 5 for tests
- **Data storage:** Plain CSV file (`data/workouts.csv`)
- **Interface:** Command-line menu

## How to Run

1. Clone the repo:
   ```
   git clone https://github.com/YOUR-USERNAME/workout-tracker.git
   cd workout-tracker
   ```
2. Compile:
   ```
   javac src/Main.java
   ```
3. Run:
   ```
   java -cp src Main
   ```

## Project Structure

```
workout-tracker/
├── src/    # Java source code
├── data/   # CSV data file
└── docs/   # Eisenhower matrix, architecture diagram
```

## Documentation

See the `docs/` folder for the Eisenhower matrix and MVP plan and the architecture diagram.

## Status

In progress. Currently at commit 1: project structure set up.
