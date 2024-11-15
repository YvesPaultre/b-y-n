package learn.fitness.models;

import java.util.ArrayList;

public class Routine {
    private int routine_id;
    private String routine_name;
    private String routine_description;
    private int routine_duration;
    private String difficulty;
    private int routine_author_id; // Alias for a trainer's user_id
    private String routine_author_name;
    private String workouts;


    // constructors
    public Routine(int routine_id, String routine_name, String routine_description, int routine_duration, String difficulty, int routine_author_id) {
        this.routine_id = routine_id;
        this.routine_name = routine_name;
        this.routine_description = routine_description;
        this.routine_duration = routine_duration;
        this.difficulty = difficulty;
        this.routine_author_id = routine_author_id;
    }

    public Routine() {}

    // getters and setters

    public int getRoutine_id() {
        return routine_id;
    }

    public void setRoutine_id(int routine_id) {
        this.routine_id = routine_id;
    }

    public String getRoutine_name() {
        return routine_name;
    }

    public void setRoutine_name(String routine_name) {
        this.routine_name = routine_name;
    }

    public String getRoutine_description() {
        return routine_description;
    }

    public void setRoutine_description(String routine_description) {
        this.routine_description = routine_description;
    }

    public int getRoutine_duration() {
        return routine_duration;
    }

    public void setRoutine_duration(int routine_duration) {
        this.routine_duration = routine_duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getRoutine_author_id() {
        return routine_author_id;
    }

    public void setRoutine_author_id(int routine_author_id) {
        this.routine_author_id = routine_author_id;
    }

    public String getRoutine_author_name() {
        return routine_author_name;
    }

    public void setRoutine_author_name(String routine_author_name) { this.routine_author_name = routine_author_name; }

    public String getWorkouts() { return workouts; }

    public void setWorkouts(String workouts) { this.workouts = workouts; }
}

