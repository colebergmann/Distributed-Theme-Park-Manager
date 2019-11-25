package tayto.parkmanager;

import org.springframework.web.client.RestTemplate;
import tayto.core.AttractionAttributes;
import tayto.core.AttractionStatus;
import tayto.core.Vehicle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

        //Compare previous status with this status to see if it's different
        if (latestStatus != null && newStatus.getStage() != latestStatus.getStage()) {
            String stamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date());
            String logMessage = "Status changed from " + latestStatus.getStage() + " to " + newStatus.getStage() + " at " + stamp;
            if (!newStatus.getFaultMessage().equalsIgnoreCase("")) {
                // add the fault code
                logMessage += " - Fault code: \"" + newStatus.getFaultMessage() + "\"";
            }
        }

        // Check the ride vehicles and record their capacity
        for (Vehicle v : newStatus.getRideVehicles()) {
            if (!vehiclesSeen.contains(v.getUuid()) && v.getUuid() != "") {
                vehiclesSeen.add(v.getUuid());
                passengersServed += v.getSeatsOccupied();
            }
        }

        latestStatus = newStatus;
    }
}
