package learn.fitness.models;

import java.util.List;

public class Workout {

    private int id;
    private String name;
    private String description;
    private int duration;
    private List<String> muscles;

    public Workout(){}

    public Workout(int id, String name, String description, int duration, List<String> muscles){
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.muscles = muscles;
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

    public List<String> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<String> muscles) {
        this.muscles = muscles;
    }
}
