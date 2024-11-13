package learn.fitness.data;

import learn.fitness.data.mappers.WorkoutMapper;
import learn.fitness.models.Workout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkoutJdbcTemplateRepository implements WorkoutRepository{

    private final JdbcTemplate jdbcTemplate;

    public WorkoutJdbcTemplateRepository(JdbcTemplate jdbcTemplate){ this.jdbcTemplate = jdbcTemplate; }

    //TODO: Add muscle_group name to select
    @Override
    public List<Workout> findAll() {
        final String sql = "select w.workout_id as workout_id, w.workout_name as workout_name, "
                + "w.workout_description as workout_description, w.workout_duration as workout_duration, "
                + "m.muscle_name as muscle_name "
                + "from workout w left join muscle m on w.muscle_id = m.muscle_id;";
        return jdbcTemplate.query(sql, new WorkoutMapper());
    }

    @Override
    public List<Workout> findByMuscleGroup(String muscleGroup) {
        final String sql = "select w.workout_id as workout_id, w.workout_name as workout_name, "
                + "w.workout_description as workout_description, w.workout_duration as workout_duration, "
                + "m.muscle_name as muscle_name "
                + "from workout w left join muscle m on w.muscle_id = m.muscle_id "
                + "left join muscle_group mg on m.mg_id = mg.mg_id "
                + "where mg_name = ?;";

        return jdbcTemplate.query(sql, new WorkoutMapper(), muscleGroup);
    }

    @Override
    public List<Workout> findByDescContent(String searchTerm) {
        return findAll().stream().filter(
                workout -> workout.getDescription().toLowerCase().contains(searchTerm.toLowerCase())
        ).toList();
    }

    @Override
    public Workout findById(int id) {
        return findAll().stream().filter(workout -> workout.getId() == id)
                .findFirst().orElse(null);
    }

    // Add and delete not supported for Workout
}
