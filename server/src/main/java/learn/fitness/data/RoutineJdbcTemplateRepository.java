package learn.fitness.data;

import learn.fitness.data.mappers.RoutineMapper;
import learn.fitness.models.Routine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoutineJdbcTemplateRepository implements RoutineRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoutineJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Routine> findAll() {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id, "
                + "u.username as routine_author_name "
                + "from routine left join user u on routine.routine_author_id = u.user_id;";

        return jdbcTemplate.query(sql, new RoutineMapper());
    }

    @Override
    public List<Routine> findByTrainer(int trainerId) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id, "
                + "u.username as routine_author_name "
                + "from routine left join user u on routine.routine_author_id = u.user_id "
                + "where routine_author_id = ?;";

        return jdbcTemplate.query(sql, new RoutineMapper(), trainerId);
    }

    @Override
    public List<Routine> findByDifficulty(String difficulty) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id, "
                + "u.username as routine_author_name "
                + "from routine left join user u on routine.routine_author_id = u.user_id "
                + "where difficulty = ?;";

        return jdbcTemplate.query(sql, new RoutineMapper(), difficulty);
    }

    //TODO: Need to find by routine name
    @Override
    public List<Routine> findByDescContent(String searchTerm) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id, "
                + "u.username as routine_author_name "
                + "from routine left join user u on routine.routine_author_id = u.user_id "
                + "where routine_description like ?;";

        // Add wildcards to the search term for pattern matching
        String searchPattern = "%" + searchTerm + "%";

        return jdbcTemplate.query(sql, new RoutineMapper(), searchPattern);
    }


    @Override
    public Routine findById(int routineId) {
        final String sql = "select r.routine_id, r.routine_name, r.routine_description, r.routine_duration, r.difficulty, r.routine_author_id, "
                + "u.username as routine_author_name, "
                + "group_concat(concat( w.workout_id, ':', w.workout_name)) as 'workouts' "
                + "from routine r "
                + "left join user u on r.routine_author_id = u.user_id "
                + "left join routine_workout rw on rw.routine_id = r.routine_id "
                + "left join workout w on rw.workout_id = w.workout_id "
                + "where r.routine_id = ?;";

        return jdbcTemplate.query(sql, new RoutineMapper(), routineId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    @Transactional
    public Routine add(Routine routine) {
        if(routine == null) return  null;

        final String sql = "insert into routine (routine_name, routine_description, routine_duration, difficulty, routine_author_id)"
                + "values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, routine.getRoutine_name());
            ps.setString(2, routine.getRoutine_description());
            ps.setInt(3, routine.getRoutine_duration());
            ps.setString(4, routine.getDifficulty());
            ps.setInt(5, routine.getRoutine_author_id());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) {
            return null;
        }

        updateRoutineWorkouts(routine);

        routine.setRoutine_id(keyHolder.getKey().intValue());
        return routine;
    }

    @Override
    @Transactional
    public boolean update(Routine routine) {
        if(routine == null) return false;
        final String sql = "update routine set "
                + "routine_name = ?, "
                + "routine_description = ?, "
                + "routine_duration = ?, "
                + "difficulty = ?, "
                + "routine_author_id = ? "
                + "where routine_id = ?;";

        updateRoutineWorkouts(routine);

        return jdbcTemplate.update(sql,
                routine.getRoutine_name(),
                routine.getRoutine_description(),
                routine.getRoutine_duration(),
                routine.getDifficulty(),
                routine.getRoutine_author_id(),
                routine.getRoutine_id()) > 0;
    }

    @Override
    @Transactional
    public boolean delete(int routineId) {
        jdbcTemplate.update("set SQL_SAFE_UPDATES = 0;");
        jdbcTemplate.update("delete from routine_workout rw where rw.routine_id = ?; ", routineId);
        jdbcTemplate.update("set SQL_SAFE_UPDATES = 1;");

        return jdbcTemplate.update("delete from routine where routine_id = ?;", routineId) > 0;
    }

    private void updateRoutineWorkouts(Routine routine){
        jdbcTemplate.update("set SQL_SAFE_UPDATES = 0; ");
        jdbcTemplate.update("delete from routine_workout rw where rw.routine_id = ?; ", routine.getRoutine_id());
        jdbcTemplate.update("set SQL_SAFE_UPDATES = 1");

        List<Integer> workouts = new ArrayList<>();
        for(String s : routine.getWorkouts().split(",")){
            workouts.add(Integer.parseInt(s.replaceAll("[^0-9]", "")));
        }

        for(int w : workouts){
            jdbcTemplate.update(
                    "insert into routine_workout(workout_id, routine_id) "
                    + "values(?,?);",
                    w, routine.getRoutine_id()
            );
        }
    }

    private String serialize(Routine routine) {
        return String.format("%s,%s,%s,%s,%s,%s",
                routine.getRoutine_id(),
                routine.getRoutine_name(),
                routine.getRoutine_description(),
                routine.getRoutine_duration(),
                routine.getDifficulty(),
                routine.getRoutine_author_id()
        );
    }
    private Routine deserialize(String line) {
        Routine routine = new Routine();
        String[] fields = line.split(",");
        routine.setRoutine_id(Integer.parseInt(fields[0]));
        routine.setRoutine_name(fields[1]);
        routine.setRoutine_description(fields[2]);
        routine.setRoutine_duration(Integer.parseInt(fields[3]));
        routine.setDifficulty(fields[4]);
        routine.setRoutine_author_id(Integer.parseInt(fields[5]));
        return routine;
    }
}
