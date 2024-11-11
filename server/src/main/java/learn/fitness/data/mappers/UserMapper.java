package learn.fitness.data.mappers;

import learn.fitness.models.User;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setUser_id(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setAdmin(rs.getBoolean("isAdmin"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("hashed_pw"));

        return user;
    }
}
