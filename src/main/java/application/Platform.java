package application;
import java.time.LocalTime;
public class Platform {
    private int id;
    private String platformName;
    private LocalTime nextFree;
    public Platform() {
        this.nextFree = LocalTime.of(0, 0);
    }

    public Platform(int id, String platformName) {
        this.id = id;
        this.platformName = platformName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public LocalTime getNextFree() {
        return nextFree;
    }


    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public void setNextFree(LocalTime nextFree) {
        this.nextFree = nextFree;
    }
}
