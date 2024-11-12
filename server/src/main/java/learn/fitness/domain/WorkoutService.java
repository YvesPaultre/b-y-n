package learn.fitness.domain;

import learn.fitness.data.WorkoutRepository;
import learn.fitness.models.Workout;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {
    private final WorkoutRepository repository;

    public WorkoutService(WorkoutRepository repository){ this.repository = repository; }

    public List<Workout> findAll(){
        return repository.findAll();
    }

    public List<Workout> findByMuscleGroup(String muscleGroup){
        return repository.findByMuscleGroup(muscleGroup);
    }

    public List<Workout> findByDescContent(String searchTerm){
        return repository.findByDescContent(searchTerm);
    }

    public Workout findById(int id){
        return repository.findById(id);
    }
}
