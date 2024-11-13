package learn.fitness.controllers;

import learn.fitness.domain.Result;
import learn.fitness.domain.RoutineService;
import learn.fitness.models.AppUser;
import learn.fitness.models.Goal;
import learn.fitness.models.Routine;
import learn.fitness.models.Workout;
import learn.fitness.security.JwtConverter;
import learn.fitness.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/routine")
public class RoutineController {

    private final RoutineService service;
    private final UserService userService;
    private final JwtConverter converter;

    public RoutineController(RoutineService service, UserService userService, JwtConverter converter) {
        this.service = service;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Routine> all = service.findAll();
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<?> findByTrainer(@PathVariable int trainerId) {
        try {
        List<Routine> all = service.findByTrainer(trainerId);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/diff/{difficulty}")
    public ResponseEntity<?> findByDifficulty(@PathVariable String difficulty) {
        try {
            List<Routine> all = service.findByDifficulty(difficulty);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //TODO: Fix to search by Routine Name
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<?> findByDescContent(@PathVariable String searchTerm) {
        try {
            List<Routine> all = service.findByDescContent(searchTerm);
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/id/{routineId}")
    public ResponseEntity<Routine> findById(@PathVariable int routineId) {
        Routine routine = service.findById(routineId);

        if(routine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(routine);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Routine routine, @RequestBody String jwtToken) {
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(!user.isAdmin()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Result<Routine> result = service.add(routine);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{routineId}")
    public ResponseEntity<Object> update(@PathVariable int routineId, @RequestBody Routine routine, @RequestBody String jwtToken) {
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(!user.isAdmin()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(routineId != routine.getRoutine_id()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Routine> result = service.update(routine);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> delete(@PathVariable int routineId, @RequestBody String jwtToken) {
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(!user.isAdmin()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(service.delete(routineId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
