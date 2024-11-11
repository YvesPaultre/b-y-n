package learn.fitness.data;

import learn.fitness.models.WorkoutLog;

import java.util.List;

public class WorkoutLogJdbcTemplateRepository implements WorkoutLogRepository{
    @Override
    public List<WorkoutLog> findByUser(int userId) {
        final String sql = "select l.log_id as log_id, l.user_id as user_id, l.finished as finished, "
                + "l.goal_id as goal_id, l.workout_id "
                + "from log l left join goal g on l.goal_id = g.goal_id "
                + "left join workout w on l.workout_id = w.workout_id "
                + "where l.user_id = ?;";

        return List.of();
    }

    @Override
    public WorkoutLog add(WorkoutLog log) {
        return null;
    }

    @Override
    public boolean update(WorkoutLog log) {
        return false;
    }

    @Override
    public int delete(int logId) {
        return 0;
    }
}
