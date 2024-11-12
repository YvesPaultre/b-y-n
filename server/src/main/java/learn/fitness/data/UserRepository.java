package learn.fitness.data;

import learn.fitness.models.AppUser;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository {

    AppUser getUserByUsername(String username);
    AppUser add(AppUser appUser);
    boolean update(AppUser appUser);

    @Transactional
    boolean delete(int user_id);
}
