package learn.fitness.data;

import learn.fitness.models.Routine;

import java.util.List;

public interface RoutineRepository {
    List<Routine> findAll();
    List<Routine> findByTrainer(int trainerId);
    List<Routine> findByDifficulty(String difficulty);
    List<Routine> findByDescContent(String searchTerm);
    Routine findById(int routineId);
    Routine add(Routine routine);
    boolean update(Routine routine);
    boolean delete(int routineId);
}
