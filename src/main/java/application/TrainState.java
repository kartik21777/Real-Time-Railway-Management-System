package application;

public enum TrainState {
    SCHEDULED,     // Train is planned but hasn't started its journey in the simulation
    EN_ROUTE,      // Train is moving towards a station
    ARRIVING,      // Train is in the process of arriving at a station
    AT_STATION,    // Train is currently stopped at a platform
    WAITING_FOR_PLATFORM, // Train has arrived but is waiting for an available platform
    DEPARTING,     // Train is in the process of leaving a platform
    DEPARTED       // Train has left the station
}