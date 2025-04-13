package dao;
import application.Station;
import java.sql.SQLException;
import java.util.List;

public interface StationDAO {
    /**
     * Retrieves all stations from the database, including their associated platforms.
     * @return A list of Station objects, each with its list of Platform objects.
     * @throws SQLException if a database error occurs.
     */
    List<Station> getAllStationsWithPlatforms() throws SQLException;

    /**
     * Adds a new station to the database.
     * @param name The name of the new station.
     * @return The generated ID of the newly created station.
     * @throws SQLException if a database error occurs (e.g., duplicate name).
     */
    int addStation(String name) throws SQLException;

    /**
     * Adds a new platform to a specific station in the database.
     * @param stationId The ID of the station to add the platform to.
     * @param platformName The name of the new platform.
     * @throws SQLException if a database error occurs (e.g., station not found, duplicate platform name within station).
     */
    void addPlatform(int stationId, String platformName) throws SQLException;

    /**
     * Deletes a station from the database. This should also delete associated platforms due to ON DELETE CASCADE.
     * @param stationId The ID of the station to delete.
     * @throws SQLException if a database error occurs (e.g., station not found).
     */
    void deleteStation(int stationId) throws SQLException;

    void deletePlatform(int platformId) throws SQLException;
}