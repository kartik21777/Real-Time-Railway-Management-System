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

    public static void addPlatform(Platform platform) {
        platformHeap.add(platform);
    }

    public static Platform popNextPlatform() {
        return platformHeap.poll();
    }

    public static Platform peekNextPlatform() {
        return platformHeap.peek();
    }

    public static void enqueueTrain(Train train) {
        waitingList.add(train);
        Collections.sort(waitingList, Comparator
                .comparing(Train::getArrivalTime)
                .thenComparing(Train::getPriority)
        );
    }

    public static Train dequeueTrain() {
        if (waitingList.isEmpty()) {
            return null;
        }
        return waitingList.remove(0);
    }

    public static Train peekWaitingTrain() {
        if (waitingList.isEmpty()) {
            return null;
        }
        return waitingList.get(0);
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

    public static List<Train> dequeueTrainsFromIndex(int index) {
        if (index < 0 || index >= waitingList.size()) {
            return Collections.emptyList();
        }
        List<Train> sublist = new ArrayList<>(waitingList.subList(index, waitingList.size()));
        waitingList.subList(index, waitingList.size()).clear();
        return sublist;
    }

    public static Duration getTrainDuration(Train train) {
        return Duration.between(train.getArrivalTime(), train.getDepartureTime());
    }

    public static void addProcessedTrain(Train train) {
        processedList.add(train);
        processedList.sort(Comparator.comparing(Train::getActualDeparture).reversed());
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
        Platform p1 = popNextPlatform();
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
        addPlatform(p1);
    }
    public static void tails(Train train)
    {
        List<Train> tail = dequeueTrainsFromIndex(waitingList.indexOf(train));

        for (int i = 1; i < tail.size(); i++) {
            Train t = tail.get(i);
            LocalTime at = t.getActualArrival();
            int pid = t.getPlatformId();
            Platform plat = null;
            for (Platform p : platformHeap) {
                if (p.getId() == pid) {
                    plat = p;
                    break;
                }
            }

            if (plat != null && at.isBefore(plat.getNextFree())) {
                platformHeap.remove(plat);
                plat.setNextFree(at);
                platformHeap.add(plat);
            }
        }

    }
}