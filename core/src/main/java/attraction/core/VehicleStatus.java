package attraction.core;

public enum VehicleStatus {
    STANDBY,                    // Vehicle is not on the track
    IDLE,                       // Vehicle is waiting to load/unload
    LOADING,                    // Passengers are loading on the vehicle
    UNLOADING,                  // Passengers are unloading from the vehicle
    IN_PROGRESS,                // Vehicle is in motion
    BREAKDOWN,                  // Vehicle has broken down in the middle of a track
    OUT_OF_ORDER                // Vehicle is broken but is not on the track
}
