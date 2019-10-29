package attraction.core;

// Enum for attraction status
public enum AttractionStatus {
    OPERATIONAL,                        // All the ride vehicles are working properly
    DEGRADED,                           // Some ride vehicles have faults
    OFFLINE                             // Attraction is offline / all vehicles have faults
}
