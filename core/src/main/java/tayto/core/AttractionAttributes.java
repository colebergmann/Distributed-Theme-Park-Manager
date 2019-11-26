package tayto.core;

public class AttractionAttributes {
    // Static facts
    // These facts are loaded in from a text file unique to each ride instance

    private String attractionName;
    private String attractionId;

    private int rideDuration;                           // Total ride duration in seconds
    private int passengersPerCarPerHour;                // Amount of passengers one car can handle per hour
    private int passengersPerCar;                       // Amount of seats one car has
    private double maxSpeed;                            // Max speed of a ride in km/h
    private int loops;                                  // Amount of loops
    private double heightRequired;                      // Required height to ride the attraction
    private int loadTime;                               // Average time required to load a ride vehicle
    private int vehicleCount;                           // Total number of vehicles build for this attraction

    /* Java Bean */
    public AttractionAttributes() { }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(String attractionId) {
        this.attractionId = attractionId;
    }

    public int getRideDuration() {
        return rideDuration;
    }

    public void setRideDuration(int rideDuration) {
        this.rideDuration = rideDuration;
    }

    public int getPassengersPerCar() {
        return passengersPerCar;
    }

    public void setPassengersPerCar(int passengersPerCar) {
        this.passengersPerCar = passengersPerCar;
    }

    public int getPassengersPerCarPerHour() {
        return passengersPerCarPerHour;
    }

    public void setPassengersPerCarPerHour(int passengersPerCarPerHour) {
        this.passengersPerCarPerHour = passengersPerCarPerHour;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getLoops() {
        return loops;
    }

    public void setLoops(int loops) {
        this.loops = loops;
    }

    public double getHeightRequired() {
        return heightRequired;
    }

    public void setHeightRequired(double heightRequired) {
        this.heightRequired = heightRequired;
    }

    public int getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }

    public int getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(int vehicleCount) {
        this.vehicleCount = vehicleCount;
    }
}
