package learn.fitness.controllers;

import learn.fitness.domain.Result;
import learn.fitness.domain.RoutineService;
import learn.fitness.models.Goal;
import learn.fitness.models.Routine;
import learn.fitness.models.Workout;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/routine")
public class RoutineController {

    private final RoutineService service;

    public RoutineController(RoutineService service) {this.service = service; }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Routine> all = service.findAll();
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{trainerId}")
    public ResponseEntity<?> findByTrainer(@PathVariable int trainerId) {
        try {
        List<Routine> all = service.findByTrainer(trainerId);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{difficulty}")
    public ResponseEntity<?> findByDifficulty(@PathVariable String difficulty) {
        try {
            List<Routine> all = service.findByDifficulty(difficulty);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{searchTerm}")
    public ResponseEntity<?> findByDescContent(@PathVariable String searchTerm) {
        try {
            List<Routine> all = service.findByDescContent(searchTerm);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{routineId}")
    public ResponseEntity<Routine> findById(@PathVariable int routineId) {
        Routine routine = service.findById(routineId);

        if(routine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(routine);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Routine routine) {
        Result<Routine> result = service.add(routine);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{routineId}")
    public ResponseEntity<Object> update(@PathVariable int routineId, @RequestBody Routine routine) {
        if(routineId != routine.getRoutine_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Routine> result = service.update(routine);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody Routine routine) {
        if(service.delete(routine)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
