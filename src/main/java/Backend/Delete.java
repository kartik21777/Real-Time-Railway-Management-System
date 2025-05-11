package Backend;
import application.Train;
import application.Platform;

import java.util.List;

public class Delete {
    public static void deleteTrain(Train train)
    {
        List<Train> tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train)-1);
        Models.tails(tail);
        for(int i = 2;i<tail.size();++i)
        {
            Train t = tail.get(i);
            Models.set(t);
        }
        Models.waitingList.remove(train);
    }
    public static int deletePlatform(Platform platform)
    {
        Train t = null;
        for(int i = 0;i<Models.processedList.size();++i)
        {
           t = Models.processedList.get(i);
           if(t.getPlatformId()==platform.getId()){
               platform.setNextFree(t.getActualDeparture());
               return 1;
           }
        }
            Models.platformHeap.remove(platform);
            Models.tails(Models.waitingList);
            for(int j = 0;j<Models.waitingList.size();++j)
            {
                Train t1 = Models.waitingList.get(j);
                Models.set(t1);
            }
            return 0;
    }
}
