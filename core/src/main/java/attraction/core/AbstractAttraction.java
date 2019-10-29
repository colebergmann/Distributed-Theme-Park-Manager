package attraction.core;

import java.util.ArrayList;

public abstract class AbstractAttraction {
    // Static facts
    private static String attractionName;
    private static int rideDuration;                           // Total ride duration in seconds
    private static int passengersPerCarPerHour;                // Amount of passengers one car can handle per hour
    private static double maxSpeed;                            // Max speed of a ride in km/h
    private static int loops;                                  // Amount of loops
    private static double heightRequired;                      // Required height to ride the attraction
    private static int loadTime;                               // Average time required to load a ride vehicle
    private static int unloadTime;                             // Average time required to unload a ride vehicle

    // Live statistics
    private int passengersInLine;                               // Number of passengers waiting to board the ride
    private AttractionStatus status;                            // current status. degraded = at least 1 car is broken
    private String faultMessage;                                // Fault message to display operators
    private ArrayList<AbstractVehicle> rideVehicles;            // List of ride vehicles

}