package learn.fitness.data;

import learn.fitness.models.Routine;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoutineJdbcTemplateRepository implements RoutineRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoutineJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Routine> findAll() {
        return List.of();
    }

    @Override
    public List<Routine> findByTrainer(int trainerId) {
        return List.of();
    }

    @Override
    public List<Routine> findByDifficulty(String difficulty) {
        return List.of();
    }

    @Override
    public List<Routine> findByDescContent(String searchTerm) {
        return List.of();
    }

    @Override
    public Routine findById(int routineId) {
        return null;
    }

    @Override
    public Routine add(Routine routine) {
        return null;
    }

    @Override
    public boolean update(Routine routine) {
        return false;
    }

    @Override
    public boolean delete(Routine routine) {
        return false;
    }
}
