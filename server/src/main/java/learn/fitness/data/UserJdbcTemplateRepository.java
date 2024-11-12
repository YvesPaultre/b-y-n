package learn.fitness.data;

import learn.fitness.data.mappers.UserMapper;
import learn.fitness.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;

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
        final String sql = "insert into user(username, hashed_pw, email, isAdmin) values "+
                " (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getEmail());
            ps.setBoolean(4,user.isAdmin());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0){ return null; }

        user.setUser_id(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public boolean update(User user) {
        final String sql = "update user set "+
                "username = ?, "+
                "hashed_pw = ?, "+
                "email = ?, "+
                "isAdmin = ? " +
                "where user_id = ?;";

        return jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.isAdmin(),
                user.getUser_id()) > 0;
    }

    // Stretch Goal
//    @Override
//    @Transactional
//    public boolean delete(int user_id) {
//        return false;
//    }
}
