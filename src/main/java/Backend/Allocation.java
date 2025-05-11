package Backend;

import application.Train;

import java.util.List;

public class Allocation {

    public static void allocatePlatform(Train train) {
        Models.enqueueTrain(train);
        if (Models.getLastTrain().getId() == train.getId()) {//id se check karna hai
            Models.set(train);
        }
        else {
            Models.tails(train);
            List<Train> tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train));
            for (int i = 0; i < tail.size(); i++)
            {
                Train t = tail.get(i);
                Models.set(t);
            }
        }
    }
}