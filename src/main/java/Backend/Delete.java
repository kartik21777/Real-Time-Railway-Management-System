package Backend;
import application.Train;
import application.Platform;
import java.util.ArrayList;
import java.time.LocalTime;
import java.util.List;

public class Delete {
    public static void deleteTrain(Train train,LocalTime now) {
        int idx = Models.waitingList.indexOf(train);

// 2) Non-destructively copy out the head (all before train)
//    and the tail (all from train onward)
        List<Train> head = new ArrayList<>(Models.waitingList.subList(0, idx));
        List<Train> tail = new ArrayList<>(Models.waitingList.subList(idx+1, Models.waitingList.size()));

        Models.tails(head);

        Models.waitingList.clear();
        Models.waitingList.addAll(head);
        Models.waitingList.addAll(tail);
        for (int i = 0; i < tail.size(); ++i) {
            Train t = tail.get(i);
            Models.set(t);
        }
    }
    public static Platform deletePlatform(Platform platform) {
        if(Models.platformHeap.size()==1){
            System.out.println("At least 1 Platform required");
            return platform;
        }
        Train t = null;
        for(int i = 0;i<Models.processedList.size();++i)
        {
            t = Models.processedList.get(i);
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
        Models.platformHeap.remove(platform);
        for(Platform plat : Models.platformHeap) {
            if (plat.getFlag() == 1) {
                plat.setFlag(0);
            } else {
                plat.setNextFree(LocalTime.of(0, 0));
            }
        }
        for (int j = 0; j < Models.waitingList.size(); ++j) {
            Train t1 = Models.waitingList.get(j);
            Models.set(t1);
        }
        return platform;
    }
}
