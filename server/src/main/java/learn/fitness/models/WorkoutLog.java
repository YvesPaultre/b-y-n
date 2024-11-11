package learn.fitness.models;

import java.time.LocalDateTime;

public class WorkoutLog {

    private int id;
    private int userId;
    private int goalId;
    private LocalDateTime timeFinished;

    public WorkoutLog(){}

    public WorkoutLog(int id, int userId, int goalId, LocalDateTime timeFinished){
        this.id = id;
        this.userId = userId;
        this.goalId = goalId;
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

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public LocalDateTime getTimeFinished() {
        return timeFinished;
    }

    public void setTimeFinished(LocalDateTime timeFinished) {
        this.timeFinished = timeFinished;
    }
}
