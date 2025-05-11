package Backend;
import java.time.Duration;
import application.Train;
import application.Platform;

import java.time.LocalTime;

import java.util.List;
public class AddTrain {

    public static void allocatePlatform(Train train) {
        Models.enqueueTrain(train);
        if (Models.getLastTrain().getId() == train.getId()) {//id se check karna hai
            Models.set(train);
        }
        else {
            Models.tails(train);
        }
    }
}