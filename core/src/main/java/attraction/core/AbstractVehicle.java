package attraction.core;

public abstract class AbstractVehicle {
    // Static facts
    static int capacity;                // Number of people who can fit in this ride vehicle

    // Live statistics
    int seatsOccupied;                  // Amount of seats occupied
    VehicleStatus status;               // Current status of the vehicle
}
