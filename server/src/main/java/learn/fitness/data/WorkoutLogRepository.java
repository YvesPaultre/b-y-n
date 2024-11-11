package learn.fitness.data;

import learn.fitness.models.WorkoutLog;

import java.util.List;

public interface WorkoutLogRepository {
    List<WorkoutLog> findByUser(int userId);

    WorkoutLog add(WorkoutLog log);

    boolean update(WorkoutLog log);

    int delete(int logId);
}
