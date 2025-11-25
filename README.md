

# Fitness Tracking App

A lightweight Core Java application for managing user profiles, logging workouts, tracking calories, and saving progress using file-based storage. Designed for beginners, academic submissions, and basic personal use.

---

 FeatuUser Profile

* Store and update name, age, weight, height
* Automatic BMI calculati Workout Logging

* Add exercises with name, duration, and calories burned
* Multiple entries peStatistics

* Total workouts
* Total calories burned
* Basic progress summaries

### Data Persistence

* File handling + Java serialization
* User and workout data stored across sessions

---

## Tech Stack

| Component     | Technology                    |
| ------------- | ----------------------------- |
| Language      | Java                          |
| Style         | Object-Oriented Programming   |
| Storage       | File Handling / Serialization |
| UI (optional) | Java Swing                    |
| IDE           | IntelliJ / Eclipse / NetBeans |

---

## Project Structure

```
FitnessTrackingApp/
│
├── src/
│   ├── Main.java
│   ├── User.java
│   ├── Workout.java
│   ├── WorkoutManager.java
│   └── FileHandler.java
│
├── data/
│   ├── users.dat
│   └── workouts.dat
│
└── README.md
```

---

## How to Run

1. Clone the repository

   ```
   git clone https://github.com/yourusername/Fitness-App.git
   ```

2. Open the project in any Java IDE.

3. Run `Main.java`.

