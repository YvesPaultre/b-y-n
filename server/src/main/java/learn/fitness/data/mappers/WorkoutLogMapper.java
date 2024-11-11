package learn.fitness.data.mappers;

import learn.fitness.models.WorkoutLog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutLogMapper implements RowMapper<WorkoutLog> {
    @Override
    public WorkoutLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        WorkoutLog log = new WorkoutLog();
        log.setId(rs.getInt("log_id"));
        log.setUserId(rs.getInt("user_id"));
        log.setTimeFinished(rs.getTimestamp("finished").toLocalDateTime());

        int goalId = rs.getInt("goal_id");
        if(goalId != 0){
            GoalMapper goalMapper = new GoalMapper();
            log.setGoal(goalMapper.mapRow(rs, rowNum));
        }

        WorkoutMapper workoutMapper = new WorkoutMapper();
        log.setWorkout(workoutMapper.mapRow(rs, rowNum));

        return log;
    }
}
