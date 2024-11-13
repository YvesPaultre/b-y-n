package learn.fitness.data.mappers;

import learn.fitness.models.Routine;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoutineMapper implements RowMapper<Routine> {

    @Override
        public Routine mapRow(ResultSet resultSet, int i) throws SQLException {
        Routine routine = new Routine();
        routine.setRoutine_id(resultSet.getInt("routine_id"));
        routine.setRoutine_name(resultSet.getString("routine_name"));
        routine.setRoutine_description(resultSet.getString("routine_description"));
        routine.setRoutine_duration(resultSet.getInt("routine_duration"));
        routine.setDifficulty(resultSet.getString("difficulty"));
        routine.setRoutine_author_id(resultSet.getInt("routine_author_id"));
        routine.setRoutine_author_name(resultSet.getString("routine_author_name"));
        return routine;
    }
}
