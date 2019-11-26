package tayto.parkmanager;

import org.springframework.web.client.RestTemplate;
import tayto.core.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

// Internal to this service to keep track of AttractionStatus over time
public class AttractionEntry {
    String endpoint;
    AttractionAttributes attributes;
    AttractionStatus latestStatus;
    ArrayList<String> events;

    // Vehicle fill rate is derived from these
    ArrayList<String> vehiclesSeen;
    int passengersServed;

    // Connect to remote attraction and fetch it's basic data
    public AttractionEntry(String url) {
        endpoint = url;
        events = new ArrayList<>();
        vehiclesSeen = new ArrayList<>();
        RestTemplate template = new RestTemplate();
        attributes = template.getForObject(endpoint + "/attributes", AttractionAttributes.class);
        refreshStatus();
    }

    // Refresh status
    public void refreshStatus() {
        RestTemplate template = new RestTemplate();
        AttractionStatus newStatus = template.getForObject(endpoint + "/status", AttractionStatus.class);

        updateLog(newStatus);
        updateVehicles(newStatus);

        latestStatus = newStatus;
    }

    private void updateVehicles(AttractionStatus newStatus) {
        // Check the ride vehicles and record their capacity
        for (Vehicle v : newStatus.getRideVehicles()) {
            if (!vehiclesSeen.contains(v.getUuid()) && v.getUuid() != "" && v.getUuid() != null) {
                vehiclesSeen.add(v.getUuid());
                passengersServed += v.getSeatsOccupied();
            }
        }
    }

    //Compare previous status with this status to see if it's different
    private void updateLog(AttractionStatus newStatus) {
        if (latestStatus != null && newStatus.getStage() != latestStatus.getStage()) {
            String stamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
            String logMessage = "Status changed from " + latestStatus.getStage() + " to " + newStatus.getStage() + " at " + stamp;
            if (newStatus.getFaultMessage() != null && !newStatus.getFaultMessage().equalsIgnoreCase("")) {
                // add the fault code
                logMessage += " Fault code: " + newStatus.getFaultMessage();
            }
            events.add(logMessage);
        }
    }

    public double getFillRate() {
        return Math.round(((passengersServed + 0.0)/vehiclesSeen.size()) / attributes.getPassengersPerCar() * 100);
    }

    private int vehicleCount(VehicleStage[] stage) {
        int result = 0;
        for (Vehicle v : latestStatus.getRideVehicles()) {
            if (Arrays.asList(stage).contains(v.getStage())) {
                result++;
            }
        }
        return result;
    }

    // Get wait time for attraction
    private int waitMins() {
        int operationalVehicles = vehicleCount(new VehicleStage[]{VehicleStage.IDLE, VehicleStage.IN_PROGRESS, VehicleStage.LOADING});
        double passengersPerMinute = (operationalVehicles * attributes.getPassengersPerCarPerHour())/60.0;
        return (int) (latestStatus.getPassengersInLine()/passengersPerMinute);
    }

    // Generate a list of suggestions for the ride manager - ex: add more vehicles, improve fill rate, etc
    public ArrayList<String> getSuggestions() {
        ArrayList<String> suggestions = new ArrayList<>();
        if (getFillRate() < 80.0) {
            suggestions.add("Vehicle fill rate is less than 80%, ask loading staff to be more efficient");
        }
        if (waitMins() > 60 && vehicleCount(new VehicleStage[]{VehicleStage.STORED}) > 0) {
            suggestions.add("Wait time is long and available vehicles are in storage, add more vehicles to the track");
        }
        //TODO: Add more suggestions (maybe to service vehicles if more than 30% are broken?
        return suggestions;
    }

    public AttractionDetails getDetails() {
        AttractionDetails summary = new AttractionDetails();
        summary.setAttributes(attributes);
        summary.setEvents(events);
        summary.setStatus(latestStatus);
        summary.setSuggestions(getSuggestions());
        summary.setVehicleFillRate(getFillRate());
        summary.setVehiclesInService(vehicleCount(new VehicleStage[]{VehicleStage.IDLE, VehicleStage.IN_PROGRESS, VehicleStage.LOADING}));
        summary.setTotalBrokenVehicles(vehicleCount(new VehicleStage[]{VehicleStage.OUT_OF_ORDER, VehicleStage.BREAKDOWN}));
        summary.setVehiclesInStorage(vehicleCount(new VehicleStage[]{VehicleStage.STORED}));
        summary.setWaitMins(waitMins());
        return summary;
    }

    public String performAction(String action) {
        try {
            RestTemplate template = new RestTemplate();
            String requestUrl = endpoint + "/action/" + action;
            String result = template.getForObject(requestUrl, String.class);
            events.add("Executed action: " + action);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Unable to connect to remote";
        }
    }
}
