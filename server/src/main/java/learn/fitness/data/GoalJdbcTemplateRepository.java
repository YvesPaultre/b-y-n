package learn.fitness.data;

import learn.fitness.data.mappers.GoalMapper;
import learn.fitness.models.Goal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class GoalJdbcTemplateRepository implements GoalRepository {

    private final JdbcTemplate jdbcTemplate;

    public GoalJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Goal> findByUser(int userId) {
        final String sql = "select goal_id, goal_name, description, complete, user_id "
                + "from goal "
                + "where user_id = ?;";

        return jdbcTemplate.query(sql, new GoalMapper(), userId);
    }

    @Override
    public Goal add(Goal goal) {
        final String sql = "insert into goal (goal_name, description, complete, user_id)"
                + "values (?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, goal.getGoal_name());
            ps.setString(2, goal.getGoal_description());
            ps.setBoolean(3, goal.isComplete());
            ps.setInt(4, goal.getUser_id());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) {
            return null;
        }

        goal.setGoal_id(keyHolder.getKey().intValue());
        return goal;
    }

    @Override
    public boolean update(Goal goal) {
       final String sql = "update goal set "
               + "goal_name = ?, "
               + "description = ?, "
               + "complete = ?, "
               + "user_id = ? "
               + "where goal_id = ?;";

       return jdbcTemplate.update(sql,
               goal.getGoal_name(),
               goal.getGoal_description(),
               goal.isComplete(),
               goal.getUser_id(),
               goal.getGoal_id()) > 0;
    }

    @Override
    public boolean delete(int goalId) {
        jdbcTemplate.update("delete from goal where goal_id = ?;", goalId);
        return jdbcTemplate.update("delete from goal where goal_id = ?;", goalId) > 0;
    }

    private String serialize(Goal goal) {
        return String.format("%s,%s,%s,%s,%s",
                goal.getGoal_id(),
                goal.getGoal_name(),
                goal.getGoal_description(),
                goal.isComplete(),
                goal.getUser_id()
        );
    }

    private Goal deserialize(String line) {
        Goal goal = new Goal();
        String[] fields = line.split(",");
        goal.setGoal_id(Integer.parseInt(fields[0]));
        goal.setGoal_name(fields[1]);
        goal.setGoal_description(fields[2]);
        goal.setComplete(Boolean.parseBoolean(fields[3]));
        goal.setUser_id(Integer.parseInt(fields[4]));
        return goal;
    }
}
