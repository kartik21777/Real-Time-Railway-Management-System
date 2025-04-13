package application;
import java.util.List;

public class Station {
    private int id;
    private String name;
    private List<Platform> platforms; // A station can have multiple platforms

    public Station() {
    }

    public Station(int id, String name, List<Platform> platforms) {
        this.id = id;
        this.name = name;
        this.platforms = platforms;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlatforms(List<Platform> platforms) {
        this.platforms = platforms;
    }

}
