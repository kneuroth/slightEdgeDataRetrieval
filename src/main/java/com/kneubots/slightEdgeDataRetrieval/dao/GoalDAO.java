package com.kneubots.slightEdgeDataRetrieval.dao;

import com.kneubots.slightEdgeDataRetrieval.models.Goal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PutMapping;

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

    // Allowed to return more than one but shouldn't
    public List<Goal> getGoalById(int id) throws SQLException {
        connect();
        ArrayList<Goal> goals = new ArrayList<Goal>();
        try {
            if (connection != null) {
                String query = "SELECT * FROM Goal WHERE id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setInt(1, id);

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

    // Returns number of rows modified, should always be 1
    public int insertGoal(Goal goal) throws SQLException {
        connect();
        int modifiedRows = 0;
        try {
            if (connection != null) {
                String query = "INSERT INTO Goal ( name, description, userId) \n" +
                        "VALUES (?, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, goal.name());
                preparedStatement.setString(2, goal.description());
                preparedStatement.setInt(3, goal.userId());

                modifiedRows = preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return modifiedRows;
    }

    public int deleteGoal(int id) throws SQLException {
        connect();
        int modifiedRows = 0;
        try {
            if (connection != null) {
                String query = "DELETE FROM Goal \n" +
                        "WHERE id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, id);

                modifiedRows = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return modifiedRows;
    }


    public int updateGoal(Goal goal, int id) throws SQLException {
        connect();
        int modifiedRows = 0;
        try {
            if (connection != null) {
                String query = "UPDATE Goal\n" +
                        "SET name = ?, description = ?, userId = ?\n" +
                        "WHERE id=?;";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, goal.name());
                preparedStatement.setString(2, goal.description());
                preparedStatement.setInt(3, goal.userId());
                preparedStatement.setInt(4, id);

                modifiedRows = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        disconnect();
        return modifiedRows;
    }
}
