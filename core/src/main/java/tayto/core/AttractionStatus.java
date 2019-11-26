package tayto.core;

import java.util.ArrayList;

public class AttractionStatus {
    // Live statistics
    // These are updated on the fly as the ride runs
    private int passengersInLine;                               // Number of passengers waiting to board the ride
    private AttractionStage stage;                               // current status. degraded = at least 1 car is broken
    private String faultMessage;                                // Fault message to display operators
    private ArrayList<Vehicle> rideVehicles;              // List of ride vehicles

    /* Java bean */
    public AttractionStatus() {
        passengersInLine = 0;
        stage = AttractionStage.CLOSED;
        faultMessage = null;
        rideVehicles = new ArrayList<>();
    }

    public int getPassengersInLine() {
        return passengersInLine;
    }

    public void setPassengersInLine(int passengersInLine) {
        this.passengersInLine = passengersInLine;
    }

    public AttractionStage getStage() {
        return stage;
    }

    public void setStage(AttractionStage stage) {
        this.stage = stage;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }

    public ArrayList<Vehicle> getRideVehicles() {
        return rideVehicles;
    }

    public void setRideVehicles(ArrayList<Vehicle> rideVehicles) {
        this.rideVehicles = rideVehicles;
    }

    public int numOperaitonalVehicles() {
        int r = 0;
        for (int i = 0; i < rideVehicles.size(); i++) {
            VehicleStage s = rideVehicles.get(i).getStage();
            if (s != VehicleStage.BREAKDOWN && s != VehicleStage.OUT_OF_ORDER && s != VehicleStage.STORED) {
                r++;
            }
        }
        return r;
    }
}
