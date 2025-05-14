package Backend;

import application.Train;
import application.Platform;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.List;

public class Allocation {

    public static void allocatePlatform(Train train) {
        Models.enqueueTrain(train);
        if (Models.getLastTrain().getId() == train.getId()) {
            Models.set(train);
            return;
        }
        int idx  = Models.waitingList.indexOf(train);
        List<Train> head = new ArrayList<>( Models.waitingList.subList(0,     idx) );
        List<Train> tail = new ArrayList<>( Models.waitingList.subList(idx+1, Models.waitingList.size()) );
        Models.tails(head);
        Models.waitingList.clear();
        Models.waitingList.addAll(head);
        Models.waitingList.add(train);
        Models.waitingList.addAll(tail);
        Models.set(train);
        for (Train t : tail) {
            Models.set(t);
        }
    }

    public static void addPlatform(Platform platform, LocalTime now)
    {
        if (Models.waitingList.isEmpty()&&Models.processedList.isEmpty())
        {
            Models.platformHeap.add(platform);
        }
        else if(Models.processedList.isEmpty())
        {
            Models.platformHeap.add(platform);
            List<Platform> all = new ArrayList<>(Models.platformHeap);
            for (Platform p : Models.platformHeap)
            {
                p.setNextFree(now);
            }
            Models.platformHeap.clear();
            Models.platformHeap.addAll(all);

            for (int i = 0; i < Models.waitingList.size(); i++)
            {
                Train t = Models.waitingList.get(i);
                int pid = t.getPlatformId();
                Platform plat = null;
                for (Platform p : Models.platformHeap) {
                    if (p.getId() == pid) {
                        plat = p;
                        break;
                    }
                }
                if(plat.getFlag()==0)
                {
                    plat.setFlag(1);
                    Models.platformHeap.remove(plat);
                    plat.setNextFree(t.getActualDeparture());
                    Models.platformHeap.add(plat);
                    continue;
                }
                Models.set(t);
            }
        }
        else if(Models.waitingList.isEmpty())
        {
            Models.platformHeap.add(platform);
        }
        else
        {
            Models.platformHeap.add(platform);
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
                plat.setFlag(1);
                Models.platformHeap.add(plat);
            }
            for (int i = 0; i < Models.waitingList.size(); i++)
            {
                Train t = Models.waitingList.get(i);
                int pid = t.getPlatformId();
                Platform plat = null;
                for (Platform p : Models.platformHeap) {
                    if (p.getId() == pid) {
                        plat = p;
                        break;
                    }
                }
                if(plat.getFlag()==0)
                {
                    plat.setFlag(1);
                    plat.setNextFree(t.getActualDeparture());
                    continue;
                }
            }
            for (Platform p : Models.platformHeap)
            {
                if(p.getFlag()==1)
                {
                    p.setFlag(0);
                    continue;
                }
                p.setNextFree(now);
            }
            List<Platform> all = new ArrayList<>(Models.platformHeap);
            Models.platformHeap.clear();
            Models.platformHeap.addAll(all);
            for (int i = 0; i < Models.waitingList.size(); i++)
            {
                Train t = Models.waitingList.get(i);
                int pid = t.getPlatformId();
                Platform plat = null;
                for (Platform p : Models.platformHeap) {
                    if (p.getId() == pid) {
                        plat = p;
                        break;
                    }
                }
                if(plat.getFlag()==0 && plat.getNextFree()==t.getActualDeparture())
                {
                    plat.setFlag(1);
                    continue;
                }
                Models.set(t);
            }
        }
    }
}