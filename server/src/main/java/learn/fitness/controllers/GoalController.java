package learn.fitness.controllers;

import learn.fitness.domain.GoalService;
import learn.fitness.domain.RoutineService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/goal")
public class GoalController {

    private final GoalService service;

    public GoalController(GoalService service) {this.service = service; }
}
