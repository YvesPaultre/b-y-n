package learn.fitness.models;

import java.util.List;

public class Workout {

    private int id;
    private String name;
    private String description;
    private int duration;
    private String muscle;
    private String muscleGroup;

    public Workout(){}

    public Workout(int id, String name, String description, int duration, String muscle, String muscleGroup){
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.muscle = muscle;
        this.muscleGroup = muscleGroup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }
}
