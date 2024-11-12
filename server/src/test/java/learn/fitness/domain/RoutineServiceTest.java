package learn.fitness.domain;

import learn.fitness.data.RoutineRepository;
import learn.fitness.models.Routine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class RoutineServiceTest {

    @Autowired
    RoutineService service;

    @MockBean
    RoutineRepository repository;

    // ---------------------------------------

    @Test
    void shouldAdd() {
        Routine routine = makeRoutine();
        Routine mockOut = makeRoutine();
        mockOut.setRoutine_id(1);

        when(repository.add(routine)).thenReturn(mockOut);

        Result<Routine> actual = service.add(routine);
        assertEquals(ResultType.SUCCESS, actual.getType());
        assertEquals(mockOut, actual.getPayload());
    }
    @Test
    void shouldNotAddWhenInvalid() {
        Routine routine = makeRoutine();
        routine.setRoutine_name("   ");
        Result<Routine> actual = service.add(routine);
        assertEquals(ResultType.INVALID, actual.getType());

        routine = makeRoutine();
        routine.setRoutine_description(null);
        actual = service.add(routine);
        assertEquals(ResultType.INVALID, actual.getType());

        routine = makeRoutine();
        routine.setRoutine_duration(0);
        actual = service.add(routine);
        assertEquals(ResultType.INVALID, actual.getType());

        routine = makeRoutine();
        routine.setDifficulty("None");
        actual = service.add(routine);
        assertEquals(ResultType.INVALID, actual.getType());

    }    @Test
    void shouldUpdate() {
        Routine routine = makeRoutine();
        routine.setRoutine_id(1);

        when(repository.update(routine)).thenReturn(true);

        Result<Routine> actual = service.update(routine);
        assertEquals(ResultType.SUCCESS, actual.getType());
    }
    @Test
    void shouldNotUpdateWhenInvalid() {
        Routine routine = makeRoutine();
        routine.setRoutine_name("   ");
        Result<Routine> actual = service.update(routine);
        assertEquals(ResultType.INVALID, actual.getType());

        routine = makeRoutine();
        routine.setRoutine_description(null);
        actual = service.update(routine);
        assertEquals(ResultType.INVALID, actual.getType());

        routine = makeRoutine();
        routine.setRoutine_duration(0);
        actual = service.update(routine);
        assertEquals(ResultType.INVALID, actual.getType());

        routine = makeRoutine();
        routine.setDifficulty("None");
        actual = service.update(routine);
        assertEquals(ResultType.INVALID, actual.getType());
    }

    Routine makeRoutine() {
        Routine routine = new Routine();
        routine.setRoutine_name("Build bigger arms");
        routine.setRoutine_description("Work on biceps and triceps so that my arms grow by 5 inches");
        routine.setRoutine_duration(30);
        routine.setDifficulty("medium");
        routine.setRoutine_author_id(1);
        return routine;
    }
}