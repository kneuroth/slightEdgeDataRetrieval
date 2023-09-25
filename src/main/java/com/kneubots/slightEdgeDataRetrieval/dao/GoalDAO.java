package com.kneubots.slightEdgeDataRetrieval.dao;

import com.kneubots.slightEdgeDataRetrieval.models.Goal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GoalDAO {

    public List<Goal> getAllGoals() {
        List<Goal> goals = new ArrayList<Goal>();
        goals.add(new Goal(1, "Touch my Toes", "I want to be able to touch my toes", 1));
        return goals;
    }
}
