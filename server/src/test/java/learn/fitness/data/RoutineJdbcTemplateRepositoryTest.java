package learn.fitness.data;

import learn.fitness.models.Routine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class RoutineJdbcTemplateRepositoryTest {

    @Autowired
    RoutineJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState state;

    @BeforeEach
    void setup(){state.set();}

    // -----------------------------------------------------------

    @Test
    void shouldFindAll() {
        List<Routine> routines = repository.findAll();
        assertEquals(4, routines.size());
    }
    @Test
    void shouldFindByTrainer() {
        List<Routine> routines = repository.findByTrainer(5);
        assertEquals(2, routines.size());
    }
    @Test
    void shouldNotFindByTrainer() {
        List<Routine> routines = repository.findByTrainer(99);
        assertEquals(0, routines.size());
    }
    @Test
    void shouldFindByDifficulty() {
        List<Routine> routines = repository.findByDifficulty("Easy");
        assertEquals(2, routines.size());
    }
    @Test
    void shouldNotFindByDifficulty() {
        List<Routine> routines = repository.findByDifficulty("None");
        assertEquals(0, routines.size());
    }
    @Test
    void shouldFindById() {
        Routine routine = repository.findById(1);
        assertEquals("Upper Body", routine.getRoutine_name());
        assertEquals("General Workout for your arms and shoulders", routine.getRoutine_description());
        assertEquals(60, routine.getRoutine_duration());
        assertEquals("Easy", routine.getDifficulty());
        assertEquals(5, routine.getRoutine_author_id());
    }
    @Test
    void shouldNotFindById() {
        Routine routine = repository.findById(99);
        assertNull(routine);
    }
    @Test
    void shouldFindByDescContent() {
        List<Routine> routines = repository.findByDescContent("gains");
        assertEquals(1, routines.size());
    }
    @Test
    void shouldNotFindByDescCount() {
        List<Routine> routines = repository.findByDescContent("DOESNOTEXISTS");
        assertEquals(0, routines.size());
    }
    @Test
    void shouldAdd() {
        Routine routine = makeRoutine();
        Routine actual = repository.add(routine);
        assertNotNull(actual);
        assertEquals("Abs workout", actual.getRoutine_name());
    }
    @Test
    void shouldNotAddNull() {
        assertNull(repository.add(null));
    }
    @Test
    void shouldUpdate() {
        Routine routine = repository.findById(4);
        routine.setRoutine_name("Very Intense Bodybuilding");
        routine.setRoutine_description(routine.getRoutine_description());
        routine.setRoutine_duration(45);
        routine.setDifficulty(routine.getDifficulty());
        routine.setRoutine_author_id(routine.getRoutine_author_id());
        assertTrue(repository.update(routine));
    }
    @Test
    void shouldNotUpdateNull() {
        Routine routine = null;
        assertFalse(repository.update(routine));
    }
    @Test
    void shouldNotDelete() {
        Routine emptyRoutine = new Routine();
        assertFalse(repository.delete(emptyRoutine));
    }
    @Test
    void shouldDelete() {
        Routine routine = repository.findById(5);
        assertTrue(repository.delete(routine));
    }

    private Routine makeRoutine() {
        Routine routine = new Routine();
        routine.setRoutine_name("Abs workout");
        routine.setRoutine_description("This workout will help you get a six pack in no time");
        routine.setRoutine_duration(30);
        routine.setDifficulty("Medium");
        routine.setRoutine_author_id(6);
        return routine;
    }
}