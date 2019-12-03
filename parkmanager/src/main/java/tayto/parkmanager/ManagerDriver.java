package tayto.parkmanager;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContextHolder;
import tayto.core.AttractionDetails;

import java.util.*;

public class ManagerDriver {
    // Setup singleton class
    private static ManagerDriver instance = null;
    public static ManagerDriver getInstance() {
        if (instance == null) {
            instance = new ManagerDriver();
        }
        return instance;
    }

    static int TICK_DELAY = 5;
    public HashMap<String, AttractionEntry> attractions;
    ArrayList<String> eurekaIds;

    public ManagerDriver() {
        attractions = new HashMap<>();
        eurekaIds = new ArrayList<>();

        // Start timer to run tick() function every
        (new Timer()).scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        tick();
                    }
                }, 0, TICK_DELAY * 1000);
    }

    // Run this code repeatedly to update attractions
    private void tick() {
        checkForNewEurekaInstances();
        for (AttractionEntry entry : attractions.values()) {
            entry.refreshStatus();
        }
    }

    public void addAttraction(String url) {
        // Create AttractionEntry
        AttractionEntry attraction = new AttractionEntry(url);

        if (attraction.attributes == null) {
            return;
        }

        //Check to make sure we don't already have this attraction stored
        String id = attraction.attributes.getAttractionId();
        if (attractions.containsKey(id)) {
            return;
        }
        attractions.put(id, attraction);
        System.out.println("[ManagerDriver] Registered new attraction: " + attraction.attributes.getAttractionId());
    }

    public Collection<AttractionDetails> getAttractionDetails() {
        ArrayList<AttractionDetails> result = new ArrayList<>();
        for (AttractionEntry entry : attractions.values()) {
            result.add(entry.getDetails());
        }
        return result;
    }

    public void checkForNewEurekaInstances() {
        Application app = EurekaServerContextHolder.getInstance().getServerContext().getRegistry().getApplication("ATTRACTION");
        if (app != null) {
            // get the instances
            for (InstanceInfo info : app.getInstances()) {
                if (!eurekaIds.contains(info.getInstanceId())) {
                    System.out.println("[ManagerDriver] Registered new Eureka instance with id " + info.getInstanceId() + " at " + info.getHomePageUrl());
                    eurekaIds.add(info.getInstanceId());
                    addAttraction(info.getHomePageUrl());
                }
            }
        }
    }
}

