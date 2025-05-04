import application.*;
import dao.*;
public class Main {
    public static void main(String [] args)
    {
        Train t1 = new Train(1,"Shatabdi","09:30","10:30","Green",1);
        Train t2 = new Train(2,"Rajdhani","11:00","12:00","Green",1);
        Platform p1 = new Platform(1,"A");
        Platform p2 = new Platform(2,"B");
        PlatformManager p = new PlatformManager();
        p.addPlatform(p1);
        p.addPlatform(p2);
        System.out.println(p.getAllPlatforms());
        TrainManager t = new TrainManager();
        t.addTrain(t1);
        t.addTrain(t2);
        System.out.println(t.getAllTrains());
        t.removeTrain(t1);
        System.out.println(t.getAllTrains());
        t.addTrain(t1);
        t.removeTrainById(2);
        System.out.println(t.getAllTrains());
        p.removePlatform(p1);
        System.out.println(p.getAllPlatforms());
        p.addPlatform(p1);
        p.removePlatformById(2);
        System.out.println(p.getAllPlatforms());
    }
}
