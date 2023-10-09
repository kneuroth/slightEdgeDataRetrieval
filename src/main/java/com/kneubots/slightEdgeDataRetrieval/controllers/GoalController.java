package com.kneubots.slightEdgeDataRetrieval.controllers;

import com.kneubots.slightEdgeDataRetrieval.dao.GoalDAO;
import com.kneubots.slightEdgeDataRetrieval.models.Goal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("")
    public List<Goal> getGoals() throws SQLException {
        return goalDAO.getAllGoals();
    }
}
