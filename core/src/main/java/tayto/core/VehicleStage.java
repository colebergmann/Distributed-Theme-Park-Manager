package tayto.core;

public enum VehicleStage {
    STORED,                     // Vehicle is not on the track but can be put into use when necessary
    IDLE,                       // Vehicle is waiting to load/unload
    LOADING,                    // Passengers are loading/unloading the vehicle
    IN_PROGRESS,                // Vehicle is in motion
    BREAKDOWN,                  // Vehicle has stopped working on the track
    OUT_OF_ORDER                // Broken vehicle in storage
}
