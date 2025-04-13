package dao.impl;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import application.Station;
import application.Train;
import dao.impl.PostgresStationDAOImpl;
import dao.impl.PostgresTrainDAOImpl;

import static org.junit.jupiter.api.Assertions.*;

public class PostgresDAOTests {

    private static Connection connection; // Connection shared across all tests in the class
    private PostgresStationDAOImpl stationDAO;
    private PostgresTrainDAOImpl trainDAO;

    // Database connection details (replace with your actual test database credentials)
    private static final String DB_URL = System.getenv("JDBC_DATABASE_URL");
    private static final String DB_USER = System.getenv("JDBC_DATABASE_USER");
    private static final String DB_PASSWORD = System.getenv("JDBC_DATABASE_PASSWORD");

    @BeforeAll
    static void setUpClass() throws SQLException {
        if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
            fail("Test database connection properties not configured (check environment variables).");
        }
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        System.out.println("Database connection established for all tests.");
    }

    @BeforeEach
    void setUpTest() throws SQLException {
        stationDAO = new PostgresStationDAOImpl();
        trainDAO = new PostgresTrainDAOImpl();

        // Clean up tables before each test
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("DELETE FROM platforms");
            st.executeUpdate("DELETE FROM stations");
            st.executeUpdate("DELETE FROM trains");
            st.executeUpdate("ALTER SEQUENCE platforms_platform_id_seq RESTART WITH 1");
            st.executeUpdate("ALTER SEQUENCE stations_station_id_seq RESTART WITH 1");
            st.executeUpdate("ALTER SEQUENCE trains_train_id_seq RESTART WITH 1");
        }
        System.out.println("Database cleaned before each test.");
    }

    @AfterEach
    void tearDownTest() {
        System.out.println("Test completed.");
    }

    @AfterAll
    static void tearDownClass() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("Database connection closed after all tests.");
        }
    }

    // StationDAO Tests
    @Test
    void testGetAllStationsWithPlatforms_empty() throws SQLException {
        List<Station> stations = stationDAO.getAllStationsWithPlatforms();
        assertTrue(stations.isEmpty());
    }

    @Test
    void testGetAllStationsWithPlatforms_multipleStationsWithPlatforms() throws SQLException {
        int stationId1 = stationDAO.addStation("Station A");
        stationDAO.addPlatform(stationId1, "Platform 1A");
        stationDAO.addPlatform(stationId1, "Platform 1B");

        int stationId2 = stationDAO.addStation("Station B");
        stationDAO.addPlatform(stationId2, "Platform 2A");

        List<Station> stations = stationDAO.getAllStationsWithPlatforms();
        assertEquals(2, stations.size());

        Station stationA = stations.stream().filter(s -> s.getId() == stationId1).findFirst().orElse(null);
        assertNotNull(stationA);
        assertEquals("Station A", stationA.getName());
        assertEquals(2, stationA.getPlatforms().size());
        assertTrue(stationA.getPlatforms().stream().anyMatch(p -> p.getPlatformName().equals("Platform 1A")));
        assertTrue(stationA.getPlatforms().stream().anyMatch(p -> p.getPlatformName().equals("Platform 1B")));
        assertEquals(stationId1, stationA.getPlatforms().get(0).getStationId());
        assertEquals(stationId1, stationA.getPlatforms().get(1).getStationId());
        assertNull(stationA.getPlatforms().get(0).getCurrentTrain());
        assertNull(stationA.getPlatforms().get(1).getCurrentTrain());

        Station stationB = stations.stream().filter(s -> s.getId() == stationId2).findFirst().orElse(null);
        assertNotNull(stationB);
        assertEquals("Station B", stationB.getName());
        assertEquals(1, stationB.getPlatforms().size());
        assertEquals("Platform 2A", stationB.getPlatforms().get(0).getPlatformName());
        assertEquals(stationId2, stationB.getPlatforms().get(0).getStationId());
        assertNull(stationB.getPlatforms().get(0).getCurrentTrain());
    }

    @Test
    void testAddStation_success() throws SQLException {
        int newStationId = stationDAO.addStation("New Station");
        assertTrue(newStationId > 0);

        List<Station> stations = stationDAO.getAllStationsWithPlatforms();
        assertEquals(1, stations.size());
        assertEquals("New Station", stations.get(0).getName());
        assertTrue(stations.get(0).getPlatforms().isEmpty());
    }

    @Test
    void testAddPlatform_success() throws SQLException {
        int stationId = stationDAO.addStation("Test Station");
        stationDAO.addPlatform(stationId, "New Platform");

        List<Station> stations = stationDAO.getAllStationsWithPlatforms();
        assertFalse(stations.isEmpty());
        Station testStation = stations.stream().filter(s -> s.getId() == stationId).findFirst().orElse(null);
        assertNotNull(testStation);
        assertEquals(1, testStation.getPlatforms().size());
        assertEquals("New Platform", testStation.getPlatforms().get(0).getPlatformName());
        assertEquals(stationId, testStation.getPlatforms().get(0).getStationId());
    }

    @Test
    void testDeleteStation_success() throws SQLException {
        int stationId = stationDAO.addStation("Station To Delete");
        stationDAO.addPlatform(stationId, "Platform A");
        stationDAO.addPlatform(stationId, "Platform B");

        List<Station> initialStations = stationDAO.getAllStationsWithPlatforms();
        assertEquals(1, initialStations.size());
        assertEquals(2, initialStations.get(0).getPlatforms().size());

        stationDAO.deleteStation(stationId);

        List<Station> remainingStations = stationDAO.getAllStationsWithPlatforms();
        assertTrue(remainingStations.isEmpty());

        // Verify platforms are also deleted (due to ON DELETE CASCADE)
        try (Statement st = connection.createStatement()) {
            java.sql.ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM platforms WHERE station_id = " + stationId);
            if (rs.next()) {
                assertEquals(0, rs.getInt(1));
            }
        }
    }

    @Test
    void testDeleteStation_nonExistingId() throws SQLException {
        // Should not throw an exception
        stationDAO.deleteStation(999);
        assertTrue(stationDAO.getAllStationsWithPlatforms().isEmpty()); // Assuming no stations were initially present
    }

    @Test
    void testDeletePlatform_success() throws SQLException {
        int stationId = stationDAO.addStation("Station With Platform");
        stationDAO.addPlatform(stationId, "Platform To Delete");
        int platformIdToDelete = -1;
        List<Station> stations = stationDAO.getAllStationsWithPlatforms();
        if (!stations.isEmpty() && !stations.get(0).getPlatforms().isEmpty()) {
            platformIdToDelete = stations.get(0).getPlatforms().get(0).getId();
        }

        assertTrue(platformIdToDelete > 0);
        stationDAO.deletePlatform(platformIdToDelete);

        List<Station> stationsAfterDelete = stationDAO.getAllStationsWithPlatforms();
        assertFalse(stationsAfterDelete.isEmpty());
        assertTrue(stationsAfterDelete.get(0).getPlatforms().isEmpty());
    }

    @Test
    void testDeletePlatform_nonExistingId() throws SQLException {
        // Should not throw an exception
        stationDAO.deletePlatform(999);
        int stationCount = stationDAO.getAllStationsWithPlatforms().size();
        try (Statement st = connection.createStatement()) {
            java.sql.ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM platforms WHERE platform_id = 999");
            if (rs.next()) {
                assertEquals(0, rs.getInt(1)); // Ensure no platform with that ID exists
            }
        }
        assertEquals(stationCount, stationDAO.getAllStationsWithPlatforms().size()); // Ensure station count remains the same
    }

    // TrainDAO Tests
    @Test
    void testGetTrainConfigById_found() throws SQLException {
        int trainId = trainDAO.addTrainConfig("T123", "Red", 1);
        Optional<Train> trainOptional = trainDAO.getTrainConfigById(trainId);
        assertTrue(trainOptional.isPresent());
        Train train = trainOptional.get();
        assertEquals(trainId, train.getId());
        assertEquals("T123", train.getTrainNumber());
        assertEquals("Red", train.getColor());
        assertEquals(1, train.getPriority());
        assertNull(train.getCurrentState());
        assertNull(train.getScheduledArrivalTime());
        assertNull(train.getActualArrivalTime());
        assertNull(train.getScheduledDepartureTime());
        assertNull(train.getAssignedPlatform());
        assertNull(train.getDestinationStation());
    }

    @Test
    void testGetTrainConfigById_notFound() throws SQLException {
        Optional<Train> trainOptional = trainDAO.getTrainConfigById(999);
        assertFalse(trainOptional.isPresent());
    }

    @Test
    void testGetAllTrainConfigs_empty() throws SQLException {
        List<Train> trains = trainDAO.getAllTrainConfigs();
        assertTrue(trains.isEmpty());
    }

    @Test
    void testGetAllTrainConfigs_multipleTrains() throws SQLException {
        trainDAO.addTrainConfig("T111", "Blue", 2);
        trainDAO.addTrainConfig("T222", "Green", 3);

        List<Train> trains = trainDAO.getAllTrainConfigs();
        assertEquals(2, trains.size());

        Train train1 = trains.stream().filter(t -> t.getTrainNumber().equals("T111")).findFirst().orElse(null);
        assertNotNull(train1);
        assertEquals("Blue", train1.getColor());
        assertEquals(2, train1.getPriority());

        Train train2 = trains.stream().filter(t -> t.getTrainNumber().equals("T222")).findFirst().orElse(null);
        assertNotNull(train2);
        assertEquals("Green", train2.getColor());
        assertEquals(3, train2.getPriority());
    }

    @Test
    void testAddTrainConfig_success() throws SQLException {
        int newTrainId = trainDAO.addTrainConfig("Express5", "Yellow", 4);
        assertTrue(newTrainId > 0);

        Optional<Train> trainOptional = trainDAO.getTrainConfigById(newTrainId);
        assertTrue(trainOptional.isPresent());
        assertEquals("Express5", trainOptional.get().getTrainNumber());
        assertEquals("Yellow", trainOptional.get().getColor());
        assertEquals(4, trainOptional.get().getPriority());
    }

    @Test
    void testDeleteTrainConfig_success() throws SQLException {
        int trainIdToDelete = trainDAO.addTrainConfig("Local1", "Gray", 1);
        Optional<Train> trainBeforeDelete = trainDAO.getTrainConfigById(trainIdToDelete);
        assertTrue(trainBeforeDelete.isPresent());

        trainDAO.deleteTrainConfig(trainIdToDelete);

        Optional<Train> trainAfterDelete = trainDAO.getTrainConfigById(trainIdToDelete);
        assertFalse(trainAfterDelete.isPresent());
    }

    @Test
    void testDeleteTrainConfig_nonExistingId() throws SQLException {
        // Should not throw an exception
        trainDAO.deleteTrainConfig(999);
        assertTrue(trainDAO.getAllTrainConfigs().isEmpty()); // Assuming no trains were initially present
    }

    @Test
    void testUpdateTrainConfig_success() throws SQLException {
        int trainId = trainDAO.addTrainConfig("OldTrain", "White", 2);
        Train updatedTrain = new Train();
        updatedTrain.setId(trainId);
        updatedTrain.setTrainNumber("NewTrainNumber");
        updatedTrain.setColor("Black");
        updatedTrain.setPriority(5);

        trainDAO.updateTrainConfig(updatedTrain);

        Optional<Train> retrievedTrain = trainDAO.getTrainConfigById(trainId);
        assertTrue(retrievedTrain.isPresent());
        assertEquals("NewTrainNumber", retrievedTrain.get().getTrainNumber());
        assertEquals("Black", retrievedTrain.get().getColor());
        assertEquals(5, retrievedTrain.get().getPriority());
    }

    @Test
    void testUpdateTrainConfig_nonExistingId() throws SQLException {
        Train nonExistingTrain = new Train();
        nonExistingTrain.setId(999);
        nonExistingTrain.setTrainNumber("NonExistent");
        nonExistingTrain.setColor("Purple");
        nonExistingTrain.setPriority(10);

        // Should not throw an exception, but also should not update anything
        trainDAO.updateTrainConfig(nonExistingTrain);
        Optional<Train> retrievedTrain = trainDAO.getTrainConfigById(999);
        assertFalse(retrievedTrain.isPresent());
    }
}