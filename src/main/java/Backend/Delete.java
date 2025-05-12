package Backend;
import application.Train;
import application.Platform;

import java.time.LocalTime;
import java.util.List;

public class Delete {
    public static void deleteTrain(Train train,LocalTime now) {
        List<Train> tail = Models.dequeueTrainsFromIndex(0,Models.waitingList.indexOf(train));
        Models.tails(tail);
        tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train),Models.waitingList.size());
        for (int i = 0; i < tail.size(); ++i) {
            Models.set(tail.get(i));
        }
        Models.waitingList.addAll(tail);
        Models.waitingList.remove(train);
    }

    public static Platform deletePlatform(Platform platform) {
        if(Models.platformHeap.size()==1){
            System.out.println("At least 1 Platform required");
            return platform;
        }
        Train t = null;
        for (int i = 0; i < Models.processedList.size(); ++i) {
            t = Models.processedList.get(i);
            if (t.getPlatformId() == platform.getId()) {
                platform.setNextFree(t.getActualDeparture());
                Models.platformHeap.remove(platform);
                Models.tails(Models.waitingList);
                for (int j = 0; j < Models.waitingList.size(); ++j) {
                    Train t1 = Models.waitingList.get(j);
                    Models.set(t1);
                }
                return platform;
            }
        }
            Models.platformHeap.remove(platform);
            platform.setNextFree(LocalTime.of(0,0));
            Models.tails(Models.waitingList);
            for (int j = 0; j < Models.waitingList.size(); ++j) {
                Train t1 = Models.waitingList.get(j);
                Models.set(t1);
            }
            return platform;
        }
    }
