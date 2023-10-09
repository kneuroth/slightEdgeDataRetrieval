package com.kneubots.slightEdgeDataRetrieval.controllers;

import com.kneubots.slightEdgeDataRetrieval.dao.GoalDAO;
import com.kneubots.slightEdgeDataRetrieval.models.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {
    private final GoalDAO goalDAO;

    @Autowired
    public GoalController(GoalDAO goalDAO) {
        this.goalDAO = goalDAO;
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Goal> getGoals() throws SQLException {
        return goalDAO.getAllGoals();
    }

    @PostMapping(path = "",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int create(@RequestBody Goal newGoal) throws SQLException {
        return goalDAO.insertGoal(newGoal);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int delete(@PathVariable int id) throws SQLException {
        return goalDAO.deleteGoal(id);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public int update(@RequestBody Goal newGoal, @PathVariable int id) throws SQLException {
        return goalDAO.updateGoal(newGoal, id);
    }
}
