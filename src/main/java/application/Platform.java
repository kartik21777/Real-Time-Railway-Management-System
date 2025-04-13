package application;
public class Platform {
    private int id;
    private String platformName;
    private int stationId; // Foreign key linking to the stations table
    private Train currentTrain; // Represents the train currently occupying this platform (initially null)

    public Platform() {
    }

    public Platform(int id, String platformName, int stationId, Train currentTrain) {
        this.id = id;
        this.platformName = platformName;
        this.stationId = stationId;
        this.currentTrain = currentTrain;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public int getStationId() {
        return stationId;
    }

    public Train getCurrentTrain() {
        return currentTrain;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setCurrentTrain(Train currentTrain) {
        this.currentTrain = currentTrain;
    }
}
