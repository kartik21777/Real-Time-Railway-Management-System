package Backend;
import application.Train;
import application.Platform;
import Backend.Models;
import java.time.LocalTime;

public class Addition {

    public static void assignTrain(Train train) {
        Platform p1 = Models.peekNextPlatform();
        if (!p1.getNextFree().isAfter(train.getArrivalTime())) {
            p1 = Models.popNextPlatform();
            train.setActualArrival(train.getArrivalTime());
            train.setActualDeparture(train.getDepartureTime());
            p1.setNextFree(train.getActualDeparture());
            Models.addPlatform(p1);
        }
    }
}

