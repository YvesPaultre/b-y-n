package learn.fitness.data;

import learn.fitness.data.mappers.WorkoutLogMapper;
import learn.fitness.models.WorkoutLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class WorkoutLogJdbcTemplateRepository implements WorkoutLogRepository{
    private final JdbcTemplate jdbcTemplate;

    public WorkoutLogJdbcTemplateRepository(JdbcTemplate jdbcTemplate){ this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<WorkoutLog> findByUser(int userId) {
        final String sql = "select l.log_id as log_id, l.user_id as user_id, l.finished as finished, "
                + "l.goal_id as goal_id, l.workout_id as workout_id, "
                + "g.goal_name as goal_name, g.description as description, g.complete as complete, "
                + "w.workout_name as workout_name, w.workout_description as workout_description, w.workout_duration as workout_duration "
                + "from log l left join goal g on l.goal_id = g.goal_id "
                + "left join workout w on l.workout_id = w.workout_id "
                + "where l.user_id = ?;";

        return jdbcTemplate.query(sql, new WorkoutLogMapper(), userId); // Internally uses GoalMapper and WorkoutMapper
    }

    @Override
    public WorkoutLog add(WorkoutLog log) {
        final String sql = "insert into log(user_id, finished, goal_id, workout_id) values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, log.getUserId());
            ps.setTimestamp(2, Timestamp.valueOf(log.getTimeFinished()));
            ps.setInt(3, log.getGoal().getGoal_id());
            ps.setInt(4, log.getWorkout().getId());

            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) return null;

        log.setId(keyHolder.getKey().intValue());
        return log;
    }

    @Override
    public boolean update(WorkoutLog log) {
        final String sql = "update log set "
                + "user_id = ?, "
                + "finished = ?, "
                + "goal_id = ?, "
                + "workout_id = ? "
                + "where log_id = ?";

        return jdbcTemplate.update(sql,
                log.getUserId(),
                Timestamp.valueOf(log.getTimeFinished()),
                log.getGoal().getGoal_id(),
                log.getWorkout().getId(),
                log.getId()
        ) > 0;
    }

    @Override
    public boolean delete(int logId) {
        // Log has dependencies but no dependents; safe to delete
        return jdbcTemplate.update("delete from log where log_id = ?;", logId) > 0;
    }
}
