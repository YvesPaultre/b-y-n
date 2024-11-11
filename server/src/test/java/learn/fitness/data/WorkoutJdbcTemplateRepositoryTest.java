package learn.fitness.data;

import learn.fitness.models.Workout;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class WorkoutJdbcTemplateRepositoryTest {
    // Read-only repository - stateless

    @Autowired
    WorkoutJdbcTemplateRepository repository;

    @Test
    void shouldFindAll() {
        List<Workout> workouts = repository.findAll();
        assertNotNull(workouts);
        assertEquals(9, workouts.size());
    }

    @Test
    void shouldFind2Arms(){
        List<Workout> armWorkouts = repository.findByMuscleGroup("arm");
        assertNotNull(armWorkouts);
        assertEquals(2, armWorkouts.size());
    }

    @Test
    void shouldFind0Bad(){
        // Should return empty, but not null
        List<Workout> badWorkouts = repository.findByMuscleGroup("bad");
        assertNotNull(badWorkouts);
        assertTrue(badWorkouts.isEmpty());
    }

    @Test
    void shouldFind2Grasp(){
        List<Workout> graspWorkouts = repository.findByDescContent("grasp");
        assertNotNull(graspWorkouts);
    }

    @Test
    void shouldFind0Blob(){
        // Should return empty, but not null
        List<Workout> blobWorkouts = repository.findByDescContent("blob");
        assertNotNull(blobWorkouts);
        assertTrue(blobWorkouts.isEmpty());
    }
}