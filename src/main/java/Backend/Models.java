package Backend;
import java.time.Duration;
import java.time.LocalTime;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.Platform;
import application.Train;

public class Models {

    public static PriorityQueue<Platform> platformHeap;
    public static List<Train> waitingList;
    public static List<Train> processedList;

    static {
        platformHeap = new PriorityQueue<>(
                Comparator.comparing(Platform::getNextFree)
                        .thenComparing(Platform::getId)
        );
        waitingList = new ArrayList<>();
        processedList = new ArrayList<>();
    }

    public static void enqueueTrain(Train train) {
        waitingList.add(train);
        Collections.sort(waitingList, Comparator
                .comparing(Train::getArrivalTime)
                .thenComparing(Train::getPriority)
        );
    }


    public static Train getLastTrain() {
        if (waitingList.isEmpty()) {
            return null;
        }
        return waitingList.get(waitingList.size() - 1);
    }

    public static List<Train> getAllWaitingTrains() {
        return Collections.unmodifiableList(waitingList);
    }

    public static List<Train> dequeueTrainsFromIndex(int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex >= waitingList.size() || startIndex > endIndex) {
            return Collections.emptyList();
        }
        List<Train> sublist = new ArrayList<>(waitingList.subList(startIndex, endIndex));
        waitingList.subList(startIndex, endIndex).clear();
        return sublist;
    }

    public static Duration getTrainDuration(Train train) {
        return Duration.between(train.getArrivalTime(), train.getDepartureTime());
    }

    public static void addProcessedTrain(Train train) {
        processedList.add(train);
    }

    public static boolean removeProcessedTrain(Train train) {
        return processedList.remove(train);
    }

    public static Train peekLastProcessedTrain() {
        if (processedList.isEmpty()) {
            return null;
        }
        return processedList.get(0);
    }

    public static List<Train> getAllProcessedTrains() {
        return Collections.unmodifiableList(processedList);
    }

    public static void clearProcessedList() {
        processedList.clear();
    }
    public static void set(Train train)
    {
        Platform p1 = platformHeap.poll();
        Duration d = getTrainDuration(train);
        if(p1.getNextFree().isAfter(train.getArrivalTime()))
        {
            LocalTime newTime = p1.getNextFree().plus(d);
            train.setActualArrival(p1.getNextFree());
            train.setActualDeparture(newTime);
            p1.setNextFree(newTime);
        }
        else{
            p1.setNextFree(train.getDepartureTime());
            train.setActualArrival(train.getArrivalTime());
            train.setActualDeparture(train.getDepartureTime());
        }
        train.setPlatformId(p1.getId());
        platformHeap.add(p1);
    }
    public static void tails(List<Train> tail)
    {
        for (int i = 0; i < tail.size(); i++) {
            Train t = tail.get(i);
            LocalTime dt = t.getActualDeparture();
            int pid = t.getPlatformId();
            Platform plat = null;
            for (Platform p : platformHeap) {
                if (p.getId() == pid) {
                    plat = p;
                    break;
                }
            }

            if (plat.getFlag()==0 && dt.isAfter(plat.getNextFree())) {
                platformHeap.remove(plat);
                plat.setNextFree(dt);
                plat.setFlag(1);
                platformHeap.add(plat);
            }
            for(Platform platform : platformHeap)
            {
                if(platform.getFlag()==1)
                {
                    platform.setFlag(0);
                }
                platform.setNextFree(LocalTime.of(0,0));
            }
        }
    }
}