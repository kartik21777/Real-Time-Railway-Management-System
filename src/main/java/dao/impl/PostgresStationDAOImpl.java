package dao.impl;

import dao.StationDAO;
import db.DatabaseUtil;
import application.Platform;
import application.Station;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresStationDAOImpl implements StationDAO {

    @Override
    public List<Station> getAllStationsWithPlatforms() throws SQLException {
        List<Station> stations = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stationStmt = null;
        ResultSet stationRs = null;
        PreparedStatement platformStmt = null;
        ResultSet platformRs = null;

        try {
            connection = DatabaseUtil.getConnection();

            // Get all stations
            String sqlStations = "SELECT station_id, name FROM stations";
            stationStmt = connection.prepareStatement(sqlStations);
            stationRs = stationStmt.executeQuery();

            while (stationRs.next()) {
                int stationId = stationRs.getInt("station_id");
                String stationName = stationRs.getString("name");
                List<Platform> platforms = new ArrayList<>();

                // Get platforms for the current station
                String sqlPlatforms = "SELECT platform_id, platform_name FROM platforms WHERE station_id = ?";
                platformStmt = connection.prepareStatement(sqlPlatforms);
                platformStmt.setInt(1, stationId);
                platformRs = platformStmt.executeQuery();

                while (platformRs.next()) {
                    int platformId = platformRs.getInt("platform_id");
                    String platformName = platformRs.getString("platform_name");
                    platforms.add(new Platform(platformId, platformName, stationId, null)); // currentTrain is initially null
                }
                stations.add(new Station(stationId, stationName, platforms));
            }
        } finally {
            if (platformRs != null) try { platformRs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (platformStmt != null) try { platformStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stationRs != null) try { stationRs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stationStmt != null) try { stationStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return stations;
    }

    @Override
    public int addStation(String name) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        int generatedId = -1;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "INSERT INTO stations (name) VALUES (?)";
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, name);
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
    public void addPlatform(int stationId, String platformName) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "INSERT INTO platforms (station_id, platform_name) VALUES (?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, stationId);
            stmt.setString(2, platformName);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void deleteStation(int stationId) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            connection = DatabaseUtil.getConnection();
            String sql = "DELETE FROM stations WHERE station_id = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, stationId);
            stmt.executeUpdate();
            // Platforms for this station will be automatically deleted due to ON DELETE CASCADE
        } finally {
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

     @Override
     public void deletePlatform(int platformId) throws SQLException {
         Connection connection = null;
         PreparedStatement stmt = null;

         try {
             connection = DatabaseUtil.getConnection();
             String sql = "DELETE FROM platforms WHERE platform_id = ?";
             stmt = connection.prepareStatement(sql);
             stmt.setInt(1, platformId);
             stmt.executeUpdate();
         } finally {
             if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
             if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
         }
     }
}