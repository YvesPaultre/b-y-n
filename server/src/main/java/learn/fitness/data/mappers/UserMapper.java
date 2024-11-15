package learn.fitness.data.mappers;

import learn.fitness.models.AppUser;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserMapper implements RowMapper<AppUser> {


    @Override
    public AppUser mapRow(ResultSet rs, int i) throws SQLException {
        return new AppUser(
                rs.getInt("user_id"),
                rs.getString("username"),
                rs.getString("hashed_pw"),
                rs.getBoolean("disabled"),
                rs.getBoolean("isAdmin") ? List.of("USER", "ADMIN") : List.of("USER"));
    }
}