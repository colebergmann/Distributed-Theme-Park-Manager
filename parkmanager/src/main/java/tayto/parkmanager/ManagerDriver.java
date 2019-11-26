package tayto.parkmanager;

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

    public ManagerDriver() {
        attractions = new HashMap<>();

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
        for (AttractionEntry entry : attractions.values()) {
            entry.refreshStatus();
        }
    }

    public void addAttraction(String url) {
        // Create AttractionEntry
        AttractionEntry attraction = new AttractionEntry(url);

        //Check to make sure we don't already have this attraction stored
        String id = attraction.attributes.getAttractionId();
        if (attractions.containsKey(id)) {
            return;
        }
        attractions.put(id, attraction);
    }

    public Collection<AttractionDetails> getAttractionDetails() {
        ArrayList<AttractionDetails> result = new ArrayList<>();
        for (AttractionEntry entry : attractions.values()) {
            result.add(entry.getDetails());
        }
        return result;
    }
}
