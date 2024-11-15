package learn.fitness.domain;

import learn.fitness.data.GoalRepository;
import learn.fitness.models.Goal;
import learn.fitness.models.Routine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GoalServiceTest {

    @Autowired
    GoalService service;

    @MockBean
    GoalRepository repository;

    // ---------------------------------------

    @Test
    void shouldAdd() {
        Goal goal = makeGoal();
        Goal mockOut = makeGoal();
        mockOut.setGoal_id(1);

        when(repository.add(goal)).thenReturn(mockOut);

        Result<Goal> actual = service.add(goal);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }
    @Test
    void shouldNotAddWhenInvalid() {
        Goal goal = makeGoal();
        goal.setGoal_name("   ");
        Result<Goal> actual = service.add(goal);
        assertEquals(ResultType.INVALID, actual.getType());

        goal = makeGoal();
        goal.setGoal_description(null);
        actual = service.add(goal);
        assertEquals(ResultType.INVALID, actual.getType());
    }
    @Test
    void shouldUpdate() {
        Goal goal = makeGoal();
        goal.setGoal_id(1);

        when(repository.update(goal)).thenReturn(true);
        Result<Goal> actual = service.update(goal);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }
    @Test
    void shouldNotUpdateWhenInvalid() {
        Goal goal = makeGoal();
        goal.setGoal_name("   ");
        Result<Goal> actual = service.update(goal);
        assertEquals(ResultType.INVALID, actual.getType());

        goal = makeGoal();
        goal.setGoal_description(null);
        actual = service.update(goal);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    Goal makeGoal() {
        Goal goal = new Goal();
        goal.setGoal_name("Bulk Up");
        goal.setGoal_description("Bulk up by adding 20 pounds of muscle");
        goal.setComplete(false);
        goal.setUser_id(1);
        return goal;
    }

}