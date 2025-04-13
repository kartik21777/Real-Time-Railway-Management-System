package application;
import java.time.LocalDateTime;

public class Train {
    private int id;
    private String trainNumber;
    private String color;
    private int priority;
    private TrainState currentState;
    private LocalDateTime scheduledArrivalTime;
    private LocalDateTime actualArrivalTime;
    private LocalDateTime scheduledDepartureTime;
    private Platform assignedPlatform; // The platform the train is currently assigned to
    private Station destinationStation; // The intended destination station

    public Train() {
    }

    public Train(int id, String trainNumber, String color, int priority, TrainState currentState, LocalDateTime scheduledArrivalTime, LocalDateTime actualArrivalTime, LocalDateTime scheduledDepartureTime, Platform assignedPlatform, Station destinationStation) {
        this.id = id;
        this.trainNumber = trainNumber;
        this.color = color;
        this.priority = priority;
        this.currentState = currentState;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.actualArrivalTime = actualArrivalTime;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.assignedPlatform = assignedPlatform;
        this.destinationStation = destinationStation;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

    public TrainState getCurrentState() {
        return currentState;
    }

    public LocalDateTime getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public LocalDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }

    public LocalDateTime getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public Platform getAssignedPlatform() {
        return assignedPlatform;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setCurrentState(TrainState currentState) {
        this.currentState = currentState;
    }

    public void setScheduledArrivalTime(LocalDateTime scheduledArrivalTime) {
        this.scheduledArrivalTime = scheduledArrivalTime;
    }

    public void setActualArrivalTime(LocalDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public void setScheduledDepartureTime(LocalDateTime scheduledDepartureTime) {
        this.scheduledDepartureTime = scheduledDepartureTime;
    }

    public void setAssignedPlatform(Platform assignedPlatform) {
        this.assignedPlatform = assignedPlatform;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }
}
