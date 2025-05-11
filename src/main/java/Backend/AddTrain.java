package Backend;
import java.time.Duration;
import application.Train;
import application.Platform;

import java.time.LocalDate;
import java.time.LocalTime;
import Backend.Models.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class AddTrain {
    static Train train;

    public static void getTrain(Train train1) {
        train = train1;
    }

    public static void main(String[] args) {
        Models.enqueueTrain(train);
        if (Models.waitingList.get(Models.waitingList.size() - 1).equals(train)) {
            Platform p1 = Models.peekNextPlatform();
            Models.popNextPlatform();
            Duration d = Models.getTrainDuration(train);
            LocalTime newTime = p1.getNextFree().plus(d);
            p1.setNextFree(newTime);
            train.setActualArrival(train.getArrivalTime());
            train.setActualDeparture(train.getDepartureTime());
            Models.addPlatform(p1);
        }
        else {
            List<Train> tail = Models.dequeueTrainsFromIndex(Models.waitingList.indexOf(train));

                for (int i = 1; i < tail.size(); i++) {
                    Train t = tail.get(i);
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
            for (int i = 0; i < tail.size(); i++)
            {
                Train t = tail.get(i);
                Platform p = Models.peekNextPlatform();
                Models.popNextPlatform();
                p.setNextFree(p.getNextFree().plus(Models.getTrainDuration(t)));

            }
            }
        }
    }