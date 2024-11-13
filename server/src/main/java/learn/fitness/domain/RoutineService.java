package learn.fitness.domain;

import learn.fitness.data.RoutineRepository;
import learn.fitness.models.Goal;
import learn.fitness.models.Routine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository repository;

    public RoutineService(RoutineRepository repository) {
        this.repository = repository;
    }

    public List<Routine> findAll() {
        return repository.findAll();
    }

    public List<Routine> findByTrainer(int trainerId) {
        return repository.findByTrainer(trainerId);
    }

    public List<Routine> findByDifficulty(String difficulty) {
        return repository.findByDifficulty(difficulty);
    }

    public List<Routine> findByDescContent(String searchTerm) {
        return repository.findByDescContent(searchTerm);
    }

    public Routine findById(int routineId) {
        return repository.findById(routineId);
    }

    public Result<Routine> update(Routine routine) {
        Result<Routine> result = validate(routine);
        if(!result.isSuccess()) {
            return result;
        }

        if (routine.getRoutine_id() <= 0) {
            result.addMessage("routineId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(routine)) {
            String msg = String.format("routineId: %s, not found", routine.getRoutine_id());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    public Result<Routine> add(Routine routine) {
        Result<Routine> result = validate(routine);
        if(!result.isSuccess()) {
            return result;
        }

        if(routine.getRoutine_id() != 0) {
            result.addMessage("routineId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        routine = repository.add(routine);
        result.setPayload(routine);
        return result;
    }

    public boolean delete(int routineId) {
        return repository.delete(routineId);
    }

    private Result<Routine> validate(Routine routine) {
        Result<Routine> result = new Result<>();

        if(routine == null) {
            result.addMessage("Routine cannot be null", ResultType.INVALID);
            return result;
        }

        String name = routine.getRoutine_name();
        if(name == null || name.isBlank()) {
            result.addMessage("Name is required", ResultType.INVALID);
            return result;
        }
        String description = routine.getRoutine_description();
        if(description == null || description.isBlank()) {
            result.addMessage("Description is required", ResultType.INVALID);
            return result;
        }

        int duration = routine.getRoutine_duration();
        if(duration <= 0) {
            result.addMessage("Duration must be greater than 0", ResultType.INVALID);
            return result;
        }

        String difficulty = routine.getDifficulty().toLowerCase();
        if(!difficulty.equals("easy") && !difficulty.equals("medium") && !difficulty.equals("hard")) {
            result.addMessage("Difficulty must be 'Easy', 'Medium' or 'Hard'", ResultType.INVALID);
            return result;
        }

        return result;
    }
}
