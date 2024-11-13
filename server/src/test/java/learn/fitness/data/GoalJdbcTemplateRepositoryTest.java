package learn.fitness.data;

import learn.fitness.models.Goal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GoalJdbcTemplateRepositoryTest {

    @Autowired
    GoalJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState state;

    @BeforeEach
    void setup(){state.set();}

    // -----------------------------------------------------------

    @Test
    void shouldFindByUser() {
       List<Goal> goals = repository.findByUser(1);
       assertEquals(2, goals.size());
    }
    @Test
    void shouldNotFindByUser() {
        List<Goal> goals = repository.findByUser(99);
        assertEquals(0, goals.size());
    }
    @Test
    void shouldAdd() {
        Goal goal = makeGoal();
        Goal actual = repository.add(goal);
        assertNotNull(actual);
        assertEquals("Build bounce", actual.getGoal_name());
        assertEquals("Work on legs and plyometrics to increase my vertical by 8 inches", actual.getGoal_description());
        assertFalse(goal.isComplete());
        assertEquals(3, goal.getUser_id());
    }
    @Test
    void shouldNotAddNull() {
        assertNull(repository.add(null));
    }
    @Test
    void shouldUpdate() {
        List<Goal> goals = repository.findByUser(3);
        Goal goal = goals.get(0);
        goal.setGoal_name("Build Athletic Solid Core");
        goal.setGoal_description("Strengthen up my abs and build a solid six pack");
        goal.setComplete(false);
        goal.setGoal_id(goal.getGoal_id());
        assertTrue(repository.update(goal));
    }
    @Test
    void shouldNotUpdate() {
        assertFalse(repository.update(null));
    }
    @Test
    void shouldNotDelete() {
        assertFalse(repository.delete(999));
    }
    @Test
    void shouldDelete() {
        assertTrue(repository.delete(2));
    }

    private Goal makeGoal() {
        Goal goal = new Goal();
        goal.setGoal_name("Build bounce");
        goal.setGoal_description("Work on legs and plyometrics to increase my vertical by 8 inches");
        goal.setComplete(false);
        goal.setUser_id(3);
        return goal;
    }
}