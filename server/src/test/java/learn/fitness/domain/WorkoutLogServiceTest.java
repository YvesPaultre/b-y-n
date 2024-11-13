package learn.fitness.domain;

import learn.fitness.data.GoalRepository;
import learn.fitness.data.WorkoutLogRepository;
import learn.fitness.data.WorkoutRepository;
import learn.fitness.models.AppUser;
import learn.fitness.models.Goal;
import learn.fitness.models.Workout;
import learn.fitness.models.WorkoutLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class WorkoutLogServiceTest {

    @Autowired
    WorkoutLogService service;

    @MockBean
    WorkoutLogRepository repository;

    @MockBean
    WorkoutRepository workoutRepository;

    @MockBean
    GoalRepository goalRepository;

    @Test
    void shouldFind2Testface(){
        Workout workout = new Workout();
        workout.setId(5);

        List<WorkoutLog> expected = List.of(new WorkoutLog(1, 1, null, workout, LocalDateTime.now()));

        AppUser testface = new AppUser(1, "testMctestface", "password", false, List.of("USER"));

        when(repository.findByUser(1)).thenReturn(expected);

        List<WorkoutLog> actual = service.findByUser(testface);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd(){
        WorkoutLog input = makeLog();
        WorkoutLog expected = makeLog();
        expected.setId(1);

        when(workoutRepository.findById(5)).thenReturn(expected.getWorkout());
        when(repository.add(input)).thenReturn(expected);

        Result<WorkoutLog> actual = service.add(input);

        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
        assertEquals(1, actual.getPayload().getId());
    }

    @Test
    void shouldNotAddIfNull(){
        Result<WorkoutLog> result = service.add(null);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddIfInvalid(){
        Result<WorkoutLog> result = service.add(new WorkoutLog(1, 999, null, null, LocalDateTime.now().plusDays(1)));

        assertFalse(result.isSuccess());
        assertEquals(2, result.getMessages().size()); // Should only return null workout and set ID messages

        Workout workout = new Workout();
        workout.setId(999);
        result = service.add(new WorkoutLog(1, 0, new Goal(), workout, LocalDateTime.now().plusDays(1)));

        assertFalse(result.isSuccess());
        assertEquals(5 ,result.getMessages().size()); // Should trigger all applicable messages
    }

    @Test
    void shouldUpdate(){
        WorkoutLog input = makeLog();
        input.setId(3);

        Result<WorkoutLog> expected = new Result<>();
        expected.setPayload(input);

        when(workoutRepository.findById(5)).thenReturn(input.getWorkout());
        when(repository.update(input)).thenReturn(true);

        Result<WorkoutLog> actual = service.update(input);
        assertTrue(actual.isSuccess());
        assertNotNull(actual.getPayload());
    }

    @Test
    void shouldNotUpdateIfNull(){
        Result<WorkoutLog> result = service.update(null);

        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotUpdateIfInvalid(){
        Result<WorkoutLog> result = service.update(new WorkoutLog(0, 999, null, null, LocalDateTime.now().plusDays(1)));

        assertFalse(result.isSuccess());
        assertEquals(2, result.getMessages().size()); // Should only return null workout and unset ID messages

        Workout workout = new Workout();
        workout.setId(999);
        result = service.add(new WorkoutLog(0, 0, new Goal(), workout, LocalDateTime.now().plusDays(1)));

        assertFalse(result.isSuccess());
        assertEquals(4 ,result.getMessages().size()); // Should trigger all applicable messages
    }

    @Test
    void shouldNotUpdateIfNoMatch(){
        WorkoutLog input = makeLog();
        input.setId(999);

        when(repository.update(input)).thenReturn(false);
        Result<WorkoutLog> result = service.update(input);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
    }

    @Test
    void shouldDelete(){
        WorkoutLog log = makeLog();
        log.setId(1);

        when(repository.findByUser(1)).thenReturn(List.of(log));
        when(repository.delete(1)).thenReturn(true);

        assertTrue(service.delete(1, 1));
    }

    @Test
    void shouldNotDeleteIfNotFoundByUser(){
        when(repository.findByUser(2)).thenReturn(List.of());
        when(repository.delete(1)).thenReturn(true); // Should not happen even if log exists!

        assertFalse(service.delete(1, 2));
    }

    WorkoutLog makeLog(){
        Workout workout = new Workout();
        workout.setId(5);

        return new WorkoutLog(0, 1, null, workout, LocalDateTime.now());
    }
}