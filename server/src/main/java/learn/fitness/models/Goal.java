package learn.fitness.models;

public class Goal {
    private int goal_id;
    private String goal_name;
    private String goal_description;
    private boolean complete;
    private int user_id;

    // constructors
    public Goal(int goal_id, String goal_name, String goal_description, boolean complete, int user_id) {
        this.goal_id = goal_id;
        this.goal_name = goal_name;
        this.goal_description = goal_description;
        this.complete = complete;
        this.user_id = user_id;
    }

    public Goal() {}

    // getters and setters

    public int getGoal_id() {
        return goal_id;
    }

    public void setGoal_id(int goal_id) {
        this.goal_id = goal_id;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public String getGoal_description() {
        return goal_description;
    }

    public void setGoal_description(String goal_description) {
        this.goal_description = goal_description;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
