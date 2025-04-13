package dao;

import application.Train;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TrainDAO {
    /**
     * Retrieves the basic configuration of a train (number, color, priority) by its ID.
     * @param trainId The ID of the train.
     * @return An Optional containing the Train object if found, or an empty Optional if not.
     * @throws SQLException if a database error occurs.
     */
    Optional<Train> getTrainConfigById(int trainId) throws SQLException;

    /**
     * Retrieves the basic configuration of all trains from the database.
     * @return A list of Train objects (containing number, color, priority).
     * @throws SQLException if a database error occurs.
     */
    List<Train> getAllTrainConfigs() throws SQLException;

    /**
     * Adds a new train configuration to the database.
     * @param trainNumber The unique number of the train.
     * @param color The color of the train.
     * @param priority The priority of the train.
     * @return The generated ID of the newly created train configuration.
     * @throws SQLException if a database error occurs (e.g., duplicate train number).
     */
    int addTrainConfig(String trainNumber, String color, int priority) throws SQLException;

    /**
     * Deletes a train configuration from the database.
     * @param trainId The ID of the train configuration to delete.
     * @throws SQLException if a database error occurs (e.g., train config not found).
     */
    void deleteTrainConfig(int trainId) throws SQLException;

    void updateTrainConfig(Train train) throws SQLException;
}