package learn.fitness.data;

import learn.fitness.data.mappers.UserMapper;
import learn.fitness.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcTemplateRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByUsername(String username) {
        final String sql = "select user_id, username, hashed_pw, email, isAdmin "+
                "from user "+
                "where username = ?;";
        User user = jdbcTemplate.query(sql, new UserMapper(), username).stream()
                .findFirst().orElse(null);

        return user;
    }

    @Override
    public User add(User user) {
        final String sql = "insert into user(user_id, username, hashed_pw, email, isAdmin) values "+
                " (?,?,?,?,?);";

        return null;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(int user_id) {
        return false;
    }
}
