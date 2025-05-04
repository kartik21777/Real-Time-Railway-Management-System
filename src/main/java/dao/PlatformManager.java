package dao;

import java.util.ArrayList;
import java.util.List;
import application.Platform;

public class PlatformManager {
    // In-memory storage for all Platform objects
    private List<Platform> platformList = new ArrayList<>();

    public void addPlatform(Platform platform) {
        platformList.add(platform);
    }


    public boolean removePlatform(Platform platform) {
        return platformList.remove(platform);
    }


    public boolean removePlatformById(int id) {
        return platformList.removeIf(p -> p.getId() == id);
    }


    public List<Platform> getAllPlatforms() {
        return platformList;
    }
}
