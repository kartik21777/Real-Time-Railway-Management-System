package Backend;
import application.Train;
import application.Platform;

import java.util.List;

public class Delete {
    public static void deleteTrain(Train train) {
        List<Train> tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train) - 1);
        Models.tails(tail);
        for (int i = 2; i < tail.size(); ++i) {
            Train t = tail.get(i);
            Models.set(t);
        }
        Models.waitingList.addAll(tail);
        Models.waitingList.remove(train);
    }

    public static Platform deletePlatform(Platform platform) {
        Train t = null;
        Platform p1 = platform;
        for (int i = 0; i < Models.processedList.size(); ++i) {
            t = Models.processedList.get(i);
            if (t.getPlatformId() == platform.getId()) {
                p1.setNextFree(t.getActualDeparture());
                Models.platformHeap.remove(platform);
                Models.tails(Models.waitingList);
                for (int j = 0; j < Models.waitingList.size(); ++j) {
                    Train t1 = Models.waitingList.get(j);
                    Models.set(t1);
                }
                return p1;
            }
        }
            Models.platformHeap.remove(platform);
            Models.tails(Models.waitingList);
            for (int j = 0; j < Models.waitingList.size(); ++j) {
                Train t1 = Models.waitingList.get(j);
                Models.set(t1);
            }
            return p1;
        }
    }
