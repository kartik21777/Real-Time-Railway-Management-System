package Backend;
import application.Train;
import application.Platform;

import java.util.List;

public class Delete {
    public static void deleteTrain(Train train)
    {
        List<Train> tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train));
        for(int i = Models.waitingList.indexOf(train)+1;i<Models.waitingList.size();++i)
        {
            Models.tails(train);
        }
        Models.waitingList.
    }
}
