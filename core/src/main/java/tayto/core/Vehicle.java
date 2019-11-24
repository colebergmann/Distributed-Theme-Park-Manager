package tayto.core;

public class Vehicle {
    // Live statistics
    // These are updated on the fly as the ride
    private int seatsOccupied;                  // Amount of seats occupied
    private VehicleStage stage;               // Current status of the vehicle

    public Vehicle() {
        stage = VehicleStage.IDLE;
    }

    public int getSeatsOccupied() {
        return seatsOccupied;
    }

    public void setSeatsOccupied(int seatsOccupied) {
        this.seatsOccupied = seatsOccupied;
    }

    public VehicleStage getStage() {
        return stage;
    }

    public void setStage(VehicleStage stage) {
        this.stage = stage;
    }
}
