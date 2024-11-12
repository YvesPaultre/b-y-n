package learn.fitness.controllers;

import learn.fitness.domain.GoalService;
import learn.fitness.domain.Result;
import learn.fitness.domain.RoutineService;
import learn.fitness.models.Goal;
import learn.fitness.models.Routine;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/goal")
public class GoalController {

    private final GoalService service;

    public GoalController(GoalService service) {this.service = service; }

    @GetMapping("/{userId}")
    public ResponseEntity<?> findByUser(@PathVariable int userId) {
        try {
            List<Goal> all = service.findByUser(userId);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Goal goal) {
        Result<Goal> result = service.add(goal);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<Object> update(@PathVariable int goalId, @RequestBody Goal goal) {
        if(goalId != goal.getGoal_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Goal> result = service.update(goal);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{goalId}")
    public ResponseEntity<Void> delete(@PathVariable int goalId) {
        if(service.delete(goalId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
