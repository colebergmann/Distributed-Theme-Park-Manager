package tayto.core;

// Enum for attraction stage
public enum AttractionStage {
    OPERATIONAL,                        // All the ride vehicles are working properly
    DEGRADED,                           // Some ride vehicles have faults
    DOWN,                               // Attraction is down / all vehicles have faults
    CLOSED                              // Currently outside of operating hours
}
