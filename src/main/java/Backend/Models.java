package Backend;
import java.util.PriorityQueue;
import java.util.Comparator;
import application.Platform;
import application.Train;

public class Models {

    private PriorityQueue<Platform> platformHeap;
    private PriorityQueue<Train> waitingQueue;

    public Models() {
        platformHeap = new PriorityQueue<>(
                Comparator.comparing(Platform::getNextFree)
                        .thenComparing(Platform::getId)
        );

        waitingQueue = new PriorityQueue<>(
                Comparator.comparing(Train::getArrivalTime)
                        .thenComparing(Train::getPriority)
        );
    }

    public void addPlatform(Platform platform) {
        platformHeap.add(platform);
    }

    public Platform popNextPlatform() {
        // Returns null if the heap is empty
        return platformHeap.poll();
    }

    public Platform peekNextPlatform() {
        // Returns null if the heap is empty
        return platformHeap.peek();
    }

    public void enqueueTrain(Train train) {
        waitingQueue.add(train);
    }

    public Train dequeueTrain() {
        // Returns null if the queue is empty
        return waitingQueue.poll();
    }

    public Train peekWaitingTrain() {
        // Returns null if the queue is empty
        return waitingQueue.peek();
    }
}

