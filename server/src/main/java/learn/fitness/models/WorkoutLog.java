package learn.fitness.models;

import java.time.LocalDateTime;

public class WorkoutLog {

    private int id;
    private int userId;
    private Goal goal;
    private Workout workout;
    private LocalDateTime timeFinished;

    public WorkoutLog(){}

    public WorkoutLog(int id, int userId, Goal goal, Workout workout, LocalDateTime timeFinished){
        this.id = id;
        this.userId = userId;
        this.goal = goal;
        this.workout = workout;
        this.timeFinished = timeFinished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(LocalDateTime timeFinished) {
        this.timeFinished = timeFinished;
    }
}
