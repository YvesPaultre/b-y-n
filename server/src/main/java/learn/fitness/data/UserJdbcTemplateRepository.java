package learn.fitness.data;

import learn.fitness.data.mappers.UserMapper;
import learn.fitness.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcTemplateRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AppUser getUserByUsername(String username) {
        final String sql = "select user_id, username, hashed_pw, email, isAdmin "+
                "from user "+
                "where username = ?;";
        AppUser appUser = jdbcTemplate.query(sql, new UserMapper(), username).stream()
                .findFirst().orElse(null);

        return appUser;
    }

    @Override
    public AppUser add(AppUser appUser) {
        final String sql = "insert into user(user_id, username, hashed_pw, email, isAdmin) values "+
                " (?,?,?,?,?);";

        return null;
    }

    @Override
    public boolean update(AppUser appUser) {
        return false;
    }

    @Override
    public boolean delete(int user_id) {
        return false;
    }
}
