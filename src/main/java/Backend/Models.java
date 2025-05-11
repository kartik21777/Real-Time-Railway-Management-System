package Backend;
import java.time.Duration;
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
}