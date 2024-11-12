package learn.fitness.controllers;

import learn.fitness.domain.RoutineService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/routine")
public class RoutineController {

    private final RoutineService service;

    public  RoutineController(RoutineService service) {this.service = service; }
}
