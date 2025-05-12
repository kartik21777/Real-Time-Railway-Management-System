package Backend;

import application.Train;
import application.Platform;

import java.time.LocalTime;
import java.util.List;

public class Allocation {

    public static void allocatePlatform(Train train) {
        Models.enqueueTrain(train);
        if (Models.getLastTrain().getId() == train.getId()) {//id se check karna hai
            Models.set(train);
        }
        else {
            List<Train> tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train));
            Models.tails(tail);
            for (int i = 0; i < tail.size(); i++)
            {
                Train t = tail.get(i);
                Models.set(t);
            }
            Models.waitingList.addAll(tail);
        }
    }
    public static void addPlatform(Platform platform)
    {
        if (Models.waitingList.isEmpty()&&Models.processedList.isEmpty())
        {
            Models.platformHeap.add(platform);
        }
        else if(Models.processedList.isEmpty())
        {
            Models.platformHeap.add(platform);
            for (int i = 0; i < Models.waitingList.size(); i++) {
                Train t = Models.waitingList.get(i);
                LocalTime at = t.getActualArrival();
                int pid = t.getPlatformId();
                Platform plat = null;
                for (Platform p : Models.platformHeap) {
                    if (p.getId() == pid) {
                        plat = p;
                        break;
                    }
                }
                if (plat != null && at.isBefore(plat.getNextFree())) {
                    Models.platformHeap.remove(plat);
                    plat.setNextFree(at);
                    Models.platformHeap.add(plat);
                }
            }
            for (int i = 0; i < Models.waitingList.size(); i++)
            {
                Train t = Models.waitingList.get(i);
                Models.set(t);
            }
        }
        else if(Models.waitingList.isEmpty())
        {
            Models.platformHeap.add(platform);
        }
        else
        {
            for (int i = 0; i < Models.processedList.size(); i++) {
                Train t = Models.processedList.get(i);
                LocalTime dt = t.getActualDeparture();
                int pid = t.getPlatformId();
                Platform plat = null;
                for (Platform p : Models.platformHeap) {
                    if (p.getId() == pid) {
                        plat = p;
                        break;
                    }
                }
                Models.platformHeap.remove(plat);
                plat.setNextFree(dt);
                Models.platformHeap.add(plat);
            }
            Models.platformHeap.add(platform);
            for (int i = 0; i < Models.waitingList.size(); i++)
            {
                Train t = Models.waitingList.get(i);
                Models.set(t);
            }

        }
    }
}