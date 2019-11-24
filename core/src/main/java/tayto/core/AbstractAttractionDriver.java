package tayto.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractAttractionDriver {
    // TICK_DELAY is how many seconds elapse between each call of tick()
    // Intended to simulate actual ride mechanics
    public static int TICK_DELAY = 2;
    // Pause simulated ride mechanics
    public boolean tickPaused = true;

    // This is a singleton class that handles all the logic of the attraction
    // It stores the AttractionAttributes and updates teh AttractionStatus based on fictional events
    private static AbstractAttractionDriver instance = null;
    public AttractionAttributes attributes;
    public AttractionStatus status;

    public static AbstractAttractionDriver getInstance() {
        return instance;
    }

    // Constructor
    // Setup our key information
    public AbstractAttractionDriver(String configPath) {
        instance = this;
        // Load attributes config
        System.out.println("[AttractionDriver] Attempting to instantiate AttractionAttributes with config: " + configPath);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            attributes = mapper.readValue(new File(configPath), AttractionAttributes.class);
            System.out.println("[AttractionDriver] Instantiation success! The following data was read from config:");
            System.out.println(ReflectionToStringBuilder.toString(attributes, ToStringStyle.MULTI_LINE_STYLE));
        } catch (Exception e) {
            System.out.println("[AttractionDriver] The given configuration file (" + configPath + ") was unable to instantiate a valid AttractionAttributes class. Check formatting and try again.\n");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }

        // Create status object and populate ride vehicles
        status = new AttractionStatus();
        for (int i = 0; i < attributes.getVehicleCount(); i++) {
            Vehicle vehicle = new Vehicle();
            vehicle.setStage(VehicleStage.STORED);
            System.out.println("[AttractionDriver] Instantiating vehicle " + i + " as ");
            status.getRideVehicles().add(vehicle);
        }

        // Call child class setup
        setup();

        // Start timer to run tick() function every
        (new Timer()).scheduleAtFixedRate(
                new TimerTask() {
                    public void run() {
                        tick();
                    }
                }, 0, TICK_DELAY * 1000);
    }

    // Get vehicle after a given vehicle in an arraylist
    public int indexAfter(int index) {
        if (index >= attributes.getVehicleCount() - 1) {
            return 0;
        }
        return index + 1;
    }

    // Helper function to get next valid vehicle
    public int getNextValidVehicleIndex(int i) {
        while (true) {
            i = indexAfter(i);
            VehicleStage vstage = status.getRideVehicles().get(i).getStage();
            if (vstage != VehicleStage.OUT_OF_ORDER && vstage != VehicleStage.STORED) {
                return i;
            }
        }
    }

    public abstract void tick();        // Run every x seconds to simulate ride mechanics
    public abstract void setup();       // Run once when ride is instantiated
    public abstract void dismissFault();
    public abstract void resolveFaults();
    public abstract void addVehicle();
}
