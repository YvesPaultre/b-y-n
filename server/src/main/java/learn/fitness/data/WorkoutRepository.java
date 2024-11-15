package learn.fitness.data;

import learn.fitness.models.Workout;

import java.util.List;

public interface WorkoutRepository {

    List<Workout> findAll();

    List<Workout> findByMuscleGroup(String muscleGroup);

    List<Workout> findByName(String searchTerm);

    Workout findById(int id);
}
