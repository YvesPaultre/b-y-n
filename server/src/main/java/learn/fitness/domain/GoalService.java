package learn.fitness.domain;

import learn.fitness.data.GoalRepository;
import learn.fitness.models.Goal;

import java.util.List;

public class GoalService {

    private final GoalRepository repository;

    public GoalService(GoalRepository repository) {
        this.repository = repository;
    }

    public List<Goal> findByUser(int userId) {
        return repository.findByUser(userId);
    }

    public Result<Goal> add(Goal goal) {
        Result<Goal> result = validate(goal);
        if(!result.isSuccess()) {
            return result;
        }

        if(goal.getGoal_id() != 0) {
            result.addMessage("goalId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        goal = repository.add(goal);
        result.setPayload(goal);
        return result;
    }

    public Result<Goal> update(Goal goal) {
        Result<Goal> result = validate(goal);
        if(!result.isSuccess()) {
            return result;
        }

        if (goal.getGoal_id() <= 0) {
            result.addMessage("goalId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(goal)) {
            String msg = String.format("goalId: %s, not found", goal.getGoal_id());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public boolean delete(int goalId) {
        return repository.delete(goalId);
    }

    private Result<Goal> validate(Goal goal) {
        Result<Goal> result = new Result<>();

        if(goal == null) {
            result.addMessage("Goal cannot be null", ResultType.INVALID);
            return result;
        }

        // goal name is required
        String name = goal.getGoal_name();
        if(name == null || name.isBlank()) {
            result.addMessage("Name is required", ResultType.INVALID);
        }

        // goal description is required
        String description = goal.getGoal_description();
        if(description == null || description.isBlank()) {
            result.addMessage("Description is required", ResultType.INVALID);
        }

        // ----------------------------------------------

        // TODO: add more validations for goals
        // can there be duplicate names/descriptions for the same user?

        int userId = goal.getUser_id();
        // TODO: check to see if userId exists - use a different repo methods

        return result;
    }
}
