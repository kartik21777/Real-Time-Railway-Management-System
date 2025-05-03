package dao;

import java.util.ArrayList;
import java.util.List;

public class TrainManager {
    // In‐memory storage for all trains
    private List<Train> trainList = new ArrayList<>();

    // Add a train to the list
    public void addTrain(Train train) {
        trainList.add(train);
    }

    // Remove a train (by object or by id)
    public boolean removeTrain(Train train) {
        return trainList.remove(train);
    }
    public boolean removeTrainById(int id) {
        return trainList.removeIf(t -> t.getId() == id);
    }

    // Retrieve the full list of trains
    public List<Train> getAllTrains() {
        return trainList;
    }
}
