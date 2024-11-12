package learn.fitness.domain;

import learn.fitness.data.GoalRepository;
import learn.fitness.data.WorkoutLogRepository;
import learn.fitness.data.WorkoutRepository;
import learn.fitness.models.AppUser;
import learn.fitness.models.WorkoutLog;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkoutLogService {
    private final WorkoutLogRepository repository;
    private final WorkoutRepository workoutRepository;
    private final GoalRepository goalRepository;

    public WorkoutLogService(WorkoutLogRepository repository, WorkoutRepository workoutRepository, GoalRepository goalRepository){
        this.repository = repository;
        this.workoutRepository = workoutRepository;
        this.goalRepository = goalRepository;
    }

    public List<WorkoutLog> findByUser(AppUser user){
        return repository.findByUser(user.getAppUserId());
    }

    public Result<WorkoutLog> add(WorkoutLog log){
        Result<WorkoutLog> result = validate(log);

        if(log.getId() > 0){
            result.addMessage("log id cannot be set for add operation", ResultType.INVALID);
        }

        if(!result.isSuccess()) return result;

        log = repository.add(log);

        if(log.getId() > 0){
            result.setPayload(log);
        } else {
            result.addMessage("failed to add log", ResultType.INVALID);
        }
        return result;
    }

    public Result<WorkoutLog> update(WorkoutLog log){
        Result<WorkoutLog> result = validate(log);

        if(log.getId() <= 0){
            result.addMessage("log id must be set for update operation", ResultType.INVALID);
        }

        if(!result.isSuccess()) return result;

        if(repository.update(log)){
            result.setPayload(log);
        } else {
            result.addMessage("log not found", ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean delete(int logId){
        return repository.delete(logId);
    }

    private Result<WorkoutLog> validate(WorkoutLog log){
        Result<WorkoutLog> result = new Result<>();

        // Null checks
        if(log == null){
            result.addMessage("log cannot be null", ResultType.INVALID);
            return result;
        }

        if(log.getGoal() == null || log.getWorkout() == null){
            result.addMessage("log cannot be null", ResultType.INVALID);
            return result;
        }

        // Logical checks
        if(log.getUserId() <= 0){
            result.addMessage("user id is required", ResultType.INVALID);
        }

        if(log.getTimeFinished().isAfter(LocalDateTime.now())){
            result.addMessage("time finished cannot be in the future", ResultType.INVALID);
        }

        verifyChildrenExist(log, result);

        return result;
    }

    private void verifyChildrenExist(WorkoutLog log, Result<WorkoutLog> result){
        if(log.getWorkout().getId() <=0 || workoutRepository.findById(log.getWorkout().getId()) == null){
            result.addMessage("workout does not exist", ResultType.INVALID);
        }

        if(log.getGoal().getGoal_id() <=0 ||
                goalRepository.findByUser(log.getUserId()).stream().noneMatch(g -> g.getGoal_id() == log.getGoal().getGoal_id())){
            // We don't care if the goal exists if it belongs to another user
            result.addMessage("goal does not exist", ResultType.INVALID);
        }
    }
}
