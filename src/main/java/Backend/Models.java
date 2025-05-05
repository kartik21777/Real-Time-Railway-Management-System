package Backend;

import java.util.PriorityQueue;
import java.util.Comparator;
import application.Platform;
import application.Train;

public class Models {

    private static PriorityQueue<Platform> platformHeap;
    private static PriorityQueue<Train> waitingQueue;

    static {
        platformHeap = new PriorityQueue<>(
                Comparator.comparing(Platform::getNextFree)
                        .thenComparing(Platform::getId)
        );
        waitingQueue = new PriorityQueue<>(
                Comparator.comparing(Train::getArrivalTime)
                        .thenComparing(Train::getPriority)
        );
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
        waitingQueue.add(train);
    }

    public static Train dequeueTrain() {
        return waitingQueue.poll();
    }

    public static Train peekWaitingTrain() {
        return waitingQueue.peek();
    }
}

