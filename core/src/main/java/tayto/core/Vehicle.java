package tayto.core;

public class Vehicle {
    // Updated as the vehicle moves around the track
    private int seatsOccupied;
    private VehicleStage stage;
    private String uuid;                // A unique ID assigned to a car every time it gets new passengers

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
