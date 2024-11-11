package learn.fitness.data;

import learn.fitness.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcTemplateRepositoryTest {

    @Autowired
    UserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState state;


    @Test
    void getUserByUsername() {
        User user = repository.getUserByUsername("testMctestface");
        assertEquals(1, user.getUser_id());
        assertEquals("testMctestface", user.getUsername());
    }

    @Test
    void shouldNotGetByBadUsername() {
        User user = repository.getUserByUsername("doseNotExist");
        assertNull(user);
    }



    @Test
    void add() {
        User user = makeUser();
//        User actual
    }

    @Test
    void update() {
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