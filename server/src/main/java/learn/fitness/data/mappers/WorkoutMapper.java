package learn.fitness.data.mappers;

import learn.fitness.models.Workout;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkoutMapper implements RowMapper<Workout> {

    @Override
    public Workout mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Workout(
              rs.getInt("workout_id"),
                rs.getString("workout_name"),
                rs.getString("workout_description"),
                rs.getInt("workout_duration"),
                rs.getString("muscle_name"),
                rs.getString("muscle_group")
        );
    }
}
