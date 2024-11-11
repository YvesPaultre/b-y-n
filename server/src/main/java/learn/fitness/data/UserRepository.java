package learn.fitness.data;

import learn.fitness.models.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository {

    User getUserByUsername(String username);
    User add(User user);
    boolean update(User user);

    // Stretch Goal
//    @Transactional
//    boolean delete(int user_id);
}
