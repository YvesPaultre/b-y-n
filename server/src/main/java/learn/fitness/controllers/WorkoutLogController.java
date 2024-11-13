package learn.fitness.controllers;

import learn.fitness.domain.Result;
import learn.fitness.domain.WorkoutLogService;
import learn.fitness.models.AppUser;
import learn.fitness.models.WorkoutLog;
import learn.fitness.security.JwtConverter;
import learn.fitness.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
@RequestMapping("/api/logs")
public class WorkoutLogController {

    private final WorkoutLogService service;
    private final UserService userService;
    private final JwtConverter converter;

    public WorkoutLogController(WorkoutLogService service, UserService userService, JwtConverter converter){
        this.service = service;
        this.userService = userService;
        this.converter = converter;
    }

    @GetMapping
    public ResponseEntity<?> findByUser(@RequestBody String jwtToken){
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        List<WorkoutLog> result = service.findByUser(user);
        if(result.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody WorkoutLog log, @RequestBody String jwtToken){
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(log.getUserId() != user.getAppUserId()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Result<WorkoutLog> result = service.add(log);

        if(!result.isSuccess()){
            return ErrorResponse.build(result);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<?> update(@PathVariable int logId, @RequestBody WorkoutLog log, @RequestBody String jwtToken){
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(log.getUserId() != user.getAppUserId()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if(log.getId() != logId){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<WorkoutLog> result = service.update(log);

        if(!result.isSuccess()){
            return ErrorResponse.build(result);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<?> delete(@PathVariable int logId, @RequestBody String jwtToken){
        AppUser user = (AppUser) userService.loadUserByUsername(converter.getUserFromToken(jwtToken).getUsername());

        if(! service.delete(logId, user.getAppUserId())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
