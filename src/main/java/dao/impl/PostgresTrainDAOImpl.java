package dao.impl;

import dao.TrainDAO;
import db.DatabaseUtil;
import application.Train;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresTrainDAOImpl implements TrainDAO {

    @Override
    public Optional<Train> getTrainConfigById(int trainId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT train_number, color, priority FROM trains WHERE train_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, trainId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Train(trainId, rs.getString("train_number"), rs.getString("color"), rs.getInt("priority"), null, null, null, null, null, null));
            } else {
                return Optional.empty();
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public List<Train> getAllTrainConfigs() throws SQLException {
        List<Train> trains = new ArrayList<>();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "SELECT train_id, train_number, color, priority FROM trains";
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                trains.add(new Train(rs.getInt("train_id"), rs.getString("train_number"), rs.getString("color"), rs.getInt("priority"), null, null, null, null, null, null));
            }
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return trains;
    }

    @Override
    public int addTrainConfig(String trainNumber, String color, int priority) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int generatedId = -1;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "INSERT INTO trains (train_number, color, priority) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, trainNumber);
            stmt.setString(2, color);
            stmt.setInt(3, priority);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedId = generatedKeys.getInt(1);
                }
            }
        } finally {
            if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return generatedId;
    }

    @Override
    public void deleteTrainConfig(int trainId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "DELETE FROM trains WHERE train_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, trainId);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

     @Override
     public void updateTrainConfig(Train train) throws SQLException {
         Connection connection = null;
         PreparedStatement stmt = null;

         try {
             connection = DatabaseUtil.getConnection();
             String sql = "UPDATE trains SET train_number = ?, color = ?, priority = ? WHERE train_id = ?";
             stmt = connection.prepareStatement(sql);
             stmt.setString(1, train.getTrainNumber());
             stmt.setString(2, train.getColor());
             stmt.setInt(3, train.getPriority());
             stmt.setInt(4, train.getId());
             stmt.executeUpdate();
         } finally {
             if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
             if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
         }
     }
}
