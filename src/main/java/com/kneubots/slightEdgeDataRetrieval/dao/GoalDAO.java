package com.kneubots.slightEdgeDataRetrieval.dao;

import com.kneubots.slightEdgeDataRetrieval.models.Goal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GoalDAO {
    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    private Connection connection;

    private void connect() throws SQLException {
        try {
            // Load driver
            Class.forName("org.sqlite.JDBC");

            // Connect to the database
            //connection = DriverManager.getConnection("jdbc:sqlite:E:\\SQL Projects\\slightEdgeDbScripts\\slight-edge.db");
            connection = DriverManager.getConnection(datasourceUrl);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            if (connection != null) {
                System.out.println("Connected to database");
            }
        }
    }

    private void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Goal> getAllGoals() throws SQLException {
        connect();
        ArrayList<Goal> goals = new ArrayList<Goal>();
        try {
            if (connection != null) {
                String query = "SELECT * FROM Goal";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                ResultSet resultSet = preparedStatement.executeQuery();

                // Process the result set and map goals
                while (resultSet.next()) {
                    // Create a Goal object and populate it with data from the result set
                    Goal goal = new Goal(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"),
                            resultSet.getInt("userId"));

                    goals.add(goal);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return goals;
    }
}
