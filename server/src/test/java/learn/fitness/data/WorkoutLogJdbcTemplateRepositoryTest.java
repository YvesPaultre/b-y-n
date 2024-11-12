package learn.fitness.data;

import learn.fitness.models.Workout;
import learn.fitness.models.WorkoutLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkoutLogJdbcTemplateRepositoryTest {
    @Autowired
    WorkoutLogJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState goodState;

    @BeforeEach
    void setup(){
        goodState.set();
    }

    @Test
    void shouldFind2Testface(){
        // 2 logs from user testMctestface (id 1)
        List<WorkoutLog> logs = repository.findByUser(1);
        assertNotNull(logs);
        assertEquals(2, logs.size());
    }

    @Test
    void shouldFind0WithBadId(){
        List<WorkoutLog> logs = repository.findByUser(999);

    }

    @Test
    void shouldAdd(){
        Workout workout = new Workout();
        workout.setId(5); // Don't actually need full Workout details; the ID is sufficient
        WorkoutLog log = new WorkoutLog(0, 5, null, workout, LocalDateTime.now()); // Null goal is acceptable

        log = repository.add(log);
        assertNotNull(log);
        assertEquals(5, log.getId());
    }

    @Test
    void shouldNotAddNull(){
        assertNull(repository.add(null));
    }

    @Test
    void shouldNotAddIfInvalid(){
        // Should not add if any fields are null
        WorkoutLog log = new WorkoutLog();
        assertNull(repository.add(log));

        // Should not add if log ID is set
        log.setId(3);
        assertNull(repository.add(log));
    }

    @Test
    void shouldUpdate(){
        Workout workout = new Workout();
        workout.setId(5);

        WorkoutLog log = new WorkoutLog();
        log.setId(3);
        log.setUserId(4);
        log.setTimeFinished(LocalDateTime.now());
        log.setWorkout(workout);

        assertTrue(repository.update(log));
    }

    @Test
    void shouldNotUpdateIfNull(){
        assertFalse(repository.update(null));
    }

    @Test
    void shouldNotUpdateIfInvalid(){
        // Should not update if any fields except goal are null
        WorkoutLog log = new WorkoutLog();
        assertFalse(repository.update(log));

        // Should not update if log ID is not set
        Workout workout = new Workout();
        log.setUserId(3);
        log.setTimeFinished(LocalDateTime.now());
        log.setWorkout(workout);

        assertFalse(repository.update(log));
    }

    @Test
    void shouldDelete(){
        // Log 4 is unused in other tests
        assertTrue(repository.delete(4));
    }

    @Test
    void shouldNotDeleteIfNotFound(){
        assertFalse(repository.delete(999));
    }
}