package tayto.core;

import java.util.ArrayList;

// This is used in the parkmanager to deliver details to clients
public class AttractionDetails {
    private AttractionAttributes attributes;
    private AttractionStatus status;

    private ArrayList<String> events;

    private double vehicleFillRate;

    private int vehiclesInService;
    private int vehiclesInStorage;
    private int totalBrokenVehicles;

    private int waitMins;

    private ArrayList<String> suggestions;

    public AttractionDetails() {
        events = new ArrayList<>();
        suggestions = new ArrayList<>();
    }

    public AttractionAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AttractionAttributes attributes) {
        this.attributes = attributes;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public double getVehicleFillRate() {
        return vehicleFillRate;
    }

    public void setStatus(AttractionStatus status) {
        this.status = status;
    }

    public void setVehicleFillRate(double vehicleFillRate) {
        this.vehicleFillRate = vehicleFillRate;
    }

    public ArrayList<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(ArrayList<String> suggestions) {
        this.suggestions = suggestions;
    }

    public AttractionStatus getStatus() {
        return status;
    }

    public void setTotalBrokenVehicles(int totalBrokenVehicles) {
        this.totalBrokenVehicles = totalBrokenVehicles;
    }

    public void setVehiclesInService(int vehiclesInService) {
        this.vehiclesInService = vehiclesInService;
    }

    public int getTotalBrokenVehicles() {
        return totalBrokenVehicles;
    }

    public int getVehiclesInService() {
        return vehiclesInService;
    }

    public int getVehiclesInStorage() {
        return vehiclesInStorage;
    }

    public void setVehiclesInStorage(int vehiclesInStorage) {
        this.vehiclesInStorage = vehiclesInStorage;
    }

    public int getWaitMins() {
        return waitMins;
    }

    public void setWaitMins(int waitMins) {
        this.waitMins = waitMins;
    }
}
