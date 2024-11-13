package learn.fitness.data;

import learn.fitness.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@AutoConfigureMockMvc
class UserJdbcTemplateRepositoryTest {

    final static int NEXT_USER_ID = 7;

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState state;

    @BeforeEach
    void setup(){state.set();}


    @Test
    void getUserByUsername() {
        AppUser user = repository.getUserByUsername("testMctestface");
        assertEquals(1, user.getAppUserId());
        assertEquals("testMctestface", user.getUsername());
    }

    @Test
    void add() {
        AppUser user = makeUser();
        AppUser actual = repository.add(user);
        assertNotNull(actual);
        assertEquals(NEXT_USER_ID, actual.getAppUserId());
    }

    @Test
    void update() {
        AppUser user = makeUser();
        user.setAppUserId(3);
        assertTrue(repository.update(user));

        user.setAppUserId(100);
        assertFalse(repository.update(user));
    }

    private AppUser makeUser(){
        AppUser user = new AppUser(0, "test", "gobbledegook", false, List.of("USER"));
        user.setAdmin(false);
        user.setEmail("test@test.com");
        return user;
    }
}