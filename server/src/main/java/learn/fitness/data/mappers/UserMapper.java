package learn.fitness.data.mappers;

import learn.fitness.models.AppUser;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<AppUser> {
    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser = new AppUser();
        appUser.setUser_id(rs.getInt("user_id"));
        appUser.setUsername(rs.getString("username"));
        appUser.setAdmin(rs.getBoolean("isAdmin"));
        appUser.setEmail(rs.getString("email"));
        appUser.setPassword(rs.getString("hashed_pw"));

        return appUser;
    }
}
