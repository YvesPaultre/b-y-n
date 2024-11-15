package learn.fitness.controllers;

import learn.fitness.domain.GoalService;
import learn.fitness.domain.Result;
import learn.fitness.domain.RoutineService;
import learn.fitness.models.AppUser;
import learn.fitness.models.Goal;
import learn.fitness.models.Routine;
import learn.fitness.security.JwtConverter;
import learn.fitness.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/goal")
public class GoalController {

    private final GoalService service;
    private final UserService userService;
    private final JwtConverter converter;

    public GoalController(GoalService service, UserService userService, JwtConverter converter) {
        this.service = service;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<?> findByUser(@RequestBody String jwtToken) {
        try {
            AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

            List<Goal> all = service.findByUser(user.getAppUserId());
            return new ResponseEntity<>(all, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Goal goal, @RequestBody String jwtToken) {
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(user.getAppUserId() != goal.getUser_id()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Result<Goal> result = service.add(goal);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{goalId}")
    public ResponseEntity<Object> update(@PathVariable int goalId, @RequestBody Goal goal, @RequestBody String jwtToken) {
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(goal.getUser_id() != user.getAppUserId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
    public ResponseEntity<Void> delete(@PathVariable int goalId, @RequestBody String jwtToken) {
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(service.delete(goalId, user.getAppUserId())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
