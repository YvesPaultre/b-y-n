package learn.fitness.data;

import learn.fitness.models.Goal;

import java.util.List;

public interface GoalRepository {
    List<Goal> findByUser(int userId);
    Goal add(Goal goal);
    boolean update(Goal goal);
    boolean delete(int goalId);
}
