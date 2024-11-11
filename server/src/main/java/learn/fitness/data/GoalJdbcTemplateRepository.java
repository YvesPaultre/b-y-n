package learn.fitness.data;

import learn.fitness.models.Goal;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class GoalJdbcTemplateRepository implements GoalRepository {

    private final JdbcTemplate jdbcTemplate;

    public GoalJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Goal> findByUser(int userId) {
        return List.of();
    }

    @Override
    public Goal add(Goal goal) {
        return null;
    }

    @Override
    public boolean update(Goal goal) {
        return false;
    }

    @Override
    public boolean delete(int goalId) {
        return false;
    }

    // private String serialize(Goal goal);
    // private Goal deserialize(String line);
}
