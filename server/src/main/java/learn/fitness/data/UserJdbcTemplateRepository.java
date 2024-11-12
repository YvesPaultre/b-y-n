package learn.fitness.data;

import learn.fitness.data.mappers.UserMapper;
import learn.fitness.models.AppUser;
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
    @Transactional
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

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,appUser.getUsername());
            ps.setString(2,appUser.getPassword());
            ps.setString(3,appUser.getEmail());
            ps.setBoolean(4,appUser.isAdmin());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0){ return null; }

        appUser.setAppUserId(keyHolder.getKey().intValue());
        return appUser;
    }

    @Override
    public boolean update(AppUser appUser) {
        final String sql = "update user set "+
                "username = ?, "+
                "hashed_pw = ?, "+
                "email = ?, "+
                "isAdmin = ? " +
                "where user_id = ?;";

        return jdbcTemplate.update(sql,
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getEmail(),
                appUser.isAdmin(),
                appUser.getAppUserId()) > 0;
    }

    // Stretch Goal
//    @Override
//    @Transactional
//    public boolean delete(int user_id) {
//        return false;
//    }
}