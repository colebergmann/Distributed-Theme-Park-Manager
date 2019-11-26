# Roller Coaster
This attraction (inheriting from AbstractAttractionDriver) simulates a typical roller coaster scenario. On init, half of the avaliable vehicles are placed onto the track and passengers start loading into the first vehicle.

Every tick, the LOADING vehicle status gets changed to IN_PROGRESS with a random amount of passengers (up to vehicle max defined in attributes) is assigned. The next valid (not in storage or broken) vehicle is set to LOADING, and the vehicle after that is set to IDLE. 

A fault randomly occurs on some ticks, which changes a vehicle status to BROKEN and pauses the ticks which essentially freezes the ride. To resume the ride, a POST request must be made to /actions/dismissfault. This changes the BROKEN vehicle statuses to OUT_OF_ORDER (which means the vehicle is broken, but in storage). The attraction status is now "Degraded" since there is a broken vehicle.

A GET request to /actions/resolvefaults must be made to change OUT_OF_ORDER vehicles to "STORED" and to return the attraction status to OPERATIONAL once again.

A GET request can be made to /actions/addvehicle to move a vehicle from storage to the track to increase ride capacity (changes status from STORED to IDLE)

(These requests should NOT be made directly to the attraction - the manager should proxy the requests)

Run this by passing in [sample-config.yml](sample-config.yml) as a command line argument

To test: `java -jar target/rollercoaster-1.0.jar sample-config.yml`