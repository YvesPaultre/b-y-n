package learn.fitness.data.mappers;

import learn.fitness.models.Goal;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalMapper implements RowMapper<Goal> {

    @Override
    public Goal mapRow(ResultSet resultSet, int i) throws SQLException {
        Goal goal = new Goal();
        goal.setGoal_id(resultSet.getInt("goal_id"));
        goal.setGoal_name(resultSet.getString("goal_name"));
        goal.setGoal_description(resultSet.getString("description"));
        goal.setComplete(resultSet.getBoolean("complete"));
        goal.setUser_id(resultSet.getInt("user_id"));
        return goal;
    }
}
