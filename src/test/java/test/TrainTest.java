package test;

import application.Train;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TrainTest {
    private Train train;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @BeforeEach
    public void setUp() {
        // initialize with id=1, name="Express", arrival="09:00", departure="10:00", color="Blue", priority=1
        train = new Train(1, "Express", "09:00", "10:00", "Blue", 1);
    }

    @Test
    public void testGetId() {
        assertEquals(1, train.getId(), "getId should return initial id");
    }

    @Test
    public void testSetId() {
        train.setId(5);
        assertEquals(5, train.getId(), "setId should update id");
    }

    @Test
    public void testGetName() {
        assertEquals("Express", train.getName(), "getName should return initial name");
    }

    @Test
    public void testSetName() {
        train.setName("Bullet");
        assertEquals("Bullet", train.getName(), "setName should update name");
    }

    @Test
    public void testGetArrivalAndDepartureTime() {
        LocalTime arrival = LocalTime.parse("09:00", formatter);
        LocalTime departure = LocalTime.parse("10:00", formatter);
        assertEquals(arrival, train.getArrivalTime(), "getArrivalTime should return parsed arrival time");
        assertEquals(departure, train.getDepartureTime(), "getDepartureTime should return parsed departure time");
    }

    @Test
    public void testSetArrivalAndDepartureTime() {
        LocalTime newArr = LocalTime.of(8, 30);
        LocalTime newDep = LocalTime.of(11, 15);
        train.setArrivalTime(newArr);
        train.setDepartureTime(newDep);
        assertEquals(newArr, train.getArrivalTime(), "setArrivalTime should update arrivalTime");
        assertEquals(newDep, train.getDepartureTime(), "setDepartureTime should update departureTime");
    }

    @Test
    public void testPlatformIdDefaultAndSetter() {
        assertEquals(0, train.getPlatformId(), "default platformId should be 0");
        train.setPlatformId(3);
        assertEquals(3, train.getPlatformId(), "setPlatformId should update platformId");
    }

    @Test
    public void testGetAndSetColor() {
        assertEquals("Blue", train.getColor(), "getColor should return initial color");
        train.setColor("Red");
        assertEquals("Red", train.getColor(), "setColor should update color");
    }

    @Test
    public void testGetAndSetPriority() {
        assertEquals(1, train.getPriority(), "getPriority should return initial priority");
        train.setPriority(5);
        assertEquals(5, train.getPriority(), "setPriority should update priority");
    }

    @Test
    public void testDefaultActualArrivalAndDeparture() {
        LocalTime defaultTime = LocalTime.of(0, 0);
        assertEquals(defaultTime, train.getActualArrival(), "default actualArrival should be 00:00");
        assertEquals(defaultTime, train.getActualDeparture(), "default actualDeparture should be 00:00");
    }

    @Test
    public void testSetActualArrivalAndDeparture() {
        LocalTime newArr = LocalTime.of(9, 5);
        LocalTime newDep = LocalTime.of(10, 10);
        train.setActualArrival(newArr);
        train.setActualDeparture(newDep);
        assertEquals(newArr, train.getActualArrival(), "setActualArrival should update actualArrival");
        assertEquals(newDep, train.getActualDeparture(), "setActualDeparture should update actualDeparture");
    }

    @Test
    public void testToString() {
        // default actualArrival and actualDeparture are "00:00"
        String expected = String.format(
                "Train{id=%d, name='%s', arrivalTime='%s', departureTime='%s', actualArrival='%s', actualDeparture='%s', color='%s', priority=%d}",
                1,
                "Express",
                train.getArrivalTime().format(formatter),
                train.getDepartureTime().format(formatter),
                train.getActualArrival().format(formatter),
                train.getActualDeparture().format(formatter),
                train.getColor(),
                train.getPriority()
        );
        assertEquals(expected, train.toString(), "toString should match expected format");
    }
}
