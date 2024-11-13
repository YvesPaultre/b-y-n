package learn.fitness.controllers;

import learn.fitness.domain.WorkoutService;
import learn.fitness.models.Workout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"}) //"http://localhost:3000"
@RequestMapping("/api/workout")
public class WorkoutController {

    private final WorkoutService service;

    public WorkoutController(WorkoutService service) {this.service = service; }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Workout> all = service.findAll();
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/id/{workoutId}")
    public ResponseEntity<Workout> findById(@PathVariable int workoutId) {
        Workout workout = service.findById(workoutId);

        if(workout == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(workout);
    }

    @GetMapping("/mg/{muscleGroup}")
    public ResponseEntity<?> findByMuscleGroup(@PathVariable String muscleGroup) {
        try {
            List<Workout> all = service.findByMuscleGroup(muscleGroup);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<?> findByName(@PathVariable String searchTerm) {
        try {
            List<Workout> all = service.findByName(searchTerm);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
