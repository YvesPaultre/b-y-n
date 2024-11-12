package learn.fitness.data;

import learn.fitness.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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
        User user = repository.getUserByUsername("testMctestface");
        assertEquals(1, user.getUser_id());
        assertEquals("testMctestface", user.getUsername());
    }

    @Test
    void add() {
        User user = makeUser();
        User actual = repository.add(user);
        assertNotNull(actual);
        assertEquals(NEXT_USER_ID, actual.getUser_id());
    }

    @Test
    void update() {
        User user = makeUser();
        user.setUser_id(3);
        assertTrue(repository.update(user));

        user.setUser_id(100);
        assertFalse(repository.update(user));
    }

    private User makeUser(){
        User user = new User();
        user.setPassword("gobbledegook");
        user.setAdmin(false);
        user.setUsername("test");
        user.setEmail("test@test.com");
        return user;
    }
}