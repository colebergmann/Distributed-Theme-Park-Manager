package tayto.rollercoaster;

import tayto.core.*;

import java.util.ArrayList;
import java.util.UUID;

public class RCDriver extends AbstractAttractionDriver {
    /*
        These are available from superclass:
        AttractionAttributes attributes;
        AttractionStatus status;
     */

    public RCDriver(String configPath) {
        super(configPath);
    }

    // Setup mechanics
    public void setup() {
        // put 50% of vehicles on the track and start loading the 1st one
        for (int i = 0; i < attributes.getVehicleCount() / 2; i++) {
            if (i == 0) {
                System.out.println("[RCDriver] Set vehicle " + i + " to " + VehicleStage.LOADING);
                status.getRideVehicles().get(i).setStage(VehicleStage.LOADING);
            } else {
                System.out.println("[RCDriver] Set vehicle " + i + " to " + VehicleStage.IDLE);
                status.getRideVehicles().get(i).setStage(VehicleStage.IDLE);
            }
        }
        status.setStage(AttractionStage.OPERATIONAL);
        status.setPassengersInLine((int) Math.round(Math.random() * 200 + 200));
        tickPaused = false;
    }

    // Every tick, a ride event should happen
    public void tick() {
        if (tickPaused) return;

        // Adjust waiting passengers
        int pcount = status.getPassengersInLine() -200 + (int) Math.round(Math.random() * 400);
        if (pcount < 100) {
            pcount = 200;
        }
        status.setPassengersInLine(pcount);
        //System.out.println("[RCDriver] New passenger count " + pcount);

        ArrayList<Vehicle> vehicles = status.getRideVehicles();
        int j = 0;
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getStage() == VehicleStage.LOADING) {
                vehicles.get(i).setStage(VehicleStage.IN_PROGRESS);
                int ppc = attributes.getPassengersPerCar();
                vehicles.get(i).setSeatsOccupied((int) Math.round(Math.random()* (ppc / 2) + ppc/2 ));
                vehicles.get(i).setUuid(UUID.randomUUID().toString());
                j = i;
                break;
            }
        }
        // Setup the next car to be loading
        j = getNextValidVehicleIndex(j);
        Vehicle loadingVehicle = vehicles.get(j);
        loadingVehicle.setStage(VehicleStage.LOADING);
        loadingVehicle.setSeatsOccupied(0);
        loadingVehicle.setUuid(null);

        // Setup the next car to be idle
        j = getNextValidVehicleIndex(j);
        Vehicle idleVehicle = vehicles.get(j);
        idleVehicle.setStage(VehicleStage.IDLE);

        // Randomly simulate a fault with a ride vehicle
        int random = (int) Math.round(Math.random() * 15);
        if (random == 2) {
            simulateFault(getNextValidVehicleIndex(j));
        }
    }

    public void simulateFault(int index) {
        Vehicle vehicle = status.getRideVehicles().get(index);
        vehicle.setStage(VehicleStage.BREAKDOWN);
        status.setStage(AttractionStage.DOWN);
        status.setFaultMessage("Safety fault detected with vehicle " + index);
        System.out.println("[RCDriver] Fault simulated with vehicle " + index + ", ride timer stopped");
        tickPaused = true;
    }

    public void dismissFault() {
        ArrayList<Vehicle> vehicles = RCDriver.getInstance().status.getRideVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getStage() == VehicleStage.BREAKDOWN) {
                vehicles.get(i).setStage(VehicleStage.OUT_OF_ORDER);
                vehicles.get(i).setSeatsOccupied(0);
                vehicles.get(i).setUuid(null);
                RCDriver.getInstance().status.setFaultMessage("Some stored vehicle(s) need attention");
                RCDriver.getInstance().status.setStage(AttractionStage.DEGRADED);
                RCDriver.getInstance().tickPaused = false;
            }
        }
        System.out.println("[RCDriver] Dismissed faults");
    }

    public void resolveFaults() {
        ArrayList<Vehicle> vehicles = RCDriver.getInstance().status.getRideVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getStage() == VehicleStage.OUT_OF_ORDER) {
                vehicles.get(i).setStage(VehicleStage.STORED);
                RCDriver.getInstance().status.setFaultMessage(null);
                RCDriver.getInstance().status.setStage(AttractionStage.OPERATIONAL);
            }
        }
        System.out.println("[RCDriver] Resolved faults");
    }

    public void addVehicle() {
        ArrayList<Vehicle> vehicles = RCDriver.getInstance().status.getRideVehicles();
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getStage() == VehicleStage.STORED) {
                vehicles.get(i).setStage(VehicleStage.IDLE);
                break;
            }
        }
        System.out.println("[RCDriver] Added vehicle");
    }
}

