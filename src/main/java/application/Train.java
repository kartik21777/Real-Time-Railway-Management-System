package application;

import java.time.LocalTime;

public class Train {
    private int id;
    private String name;
    private LocalTime arrivalTime;
    private LocalTime departureTime;
    private int platformId;
    private String color;
    private int priority;
    private LocalTime actualArrival;
    private LocalTime actualDeparture;

    public Train() {
        this.actualArrival = LocalTime.of(0,0);
        this.actualDeparture = LocalTime.of(0,0);
    }

    public Train(int id, String name, LocalTime arrivalTime, LocalTime departureTime, int platformId,
                 String color, int priority, LocalTime actualArrival, LocalTime actualDeparture) {
        this.id = id;
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.platformId = platformId;
        this.color = color;
        this.priority = priority;
        this.actualArrival = actualArrival;
        this.actualDeparture = actualDeparture;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public int getPlatformId() {
        return platformId;
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

    public LocalTime getActualArrival() {
        return actualArrival;
    }

    public LocalTime getActualDeparture() {
        return actualDeparture;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setActualArrival(LocalTime actualArrival) {
        this.actualArrival = actualArrival;
    }

    public void setActualDeparture(LocalTime actualDeparture) {
        this.actualDeparture = actualDeparture;
    }
}
