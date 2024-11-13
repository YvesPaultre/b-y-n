package learn.fitness.data;

import learn.fitness.data.mappers.RoutineMapper;
import learn.fitness.models.Routine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RoutineJdbcTemplateRepository implements RoutineRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoutineJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Routine> findAll() {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id "
                + "from routine;";

        return jdbcTemplate.query(sql, new RoutineMapper());
    }

    @Override
    public List<Routine> findByTrainer(int trainerId) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id "
                + "from routine "
                + "where routine_author_id = ?;";

        return jdbcTemplate.query(sql, new RoutineMapper(), trainerId);
    }

    @Override
    public List<Routine> findByDifficulty(String difficulty) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id "
                + "from routine "
                + "where difficulty = ?;";

        return jdbcTemplate.query(sql, new RoutineMapper(), difficulty);
    }

    @Override
    public List<Routine> findByDescContent(String searchTerm) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id "
                + "from routine "
                + "where routine_description like ?;";

        // Add wildcards to the search term for pattern matching
        String searchPattern = "%" + searchTerm + "%";

        return jdbcTemplate.query(sql, new RoutineMapper(), searchPattern);
    }

    @Override
    public Routine findById(int routineId) {
        final String sql = "select routine_id, routine_name, routine_description, routine_duration, difficulty, routine_author_id "
                + "from routine "
                + "where routine_id = ?;";

        return jdbcTemplate.query(sql, new RoutineMapper(), routineId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
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

        routine.setRoutine_id(keyHolder.getKey().intValue());
        return routine;
    }

    @Override
    public boolean update(Routine routine) {
        if(routine == null) return false;
        final String sql = "update routine set "
                + "routine_name = ?, "
                + "routine_description = ?, "
                + "routine_duration = ?, "
                + "difficulty = ?, "
                + "routine_author_id = ? "
                + "where routine_id = ?;";

        return jdbcTemplate.update(sql,
                routine.getRoutine_name(),
                routine.getRoutine_description(),
                routine.getRoutine_duration(),
                routine.getDifficulty(),
                routine.getRoutine_author_id(),
                routine.getRoutine_id()) > 0;
    }

    @Override
    public boolean delete(int routineId) {
        return jdbcTemplate.update("delete from routine where routine_id = ?;", routineId) > 0;
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
