# Distributed Theme Park Management System

## System Overview
This system is composed of two main components: several independent attractions and one management server. Each attraction does not save any state and only knows it's current operating status. The management server polls every attraction frequently to collect information and generate conclusions from it.

## Park Manager Overview
The Park Manager discovers attractions using Netflix Eureka and polls the attractions frequently to store their current state (and log changes). The PM supports both REST and GraphQL queries to get information about the attractions (more info on the [parkmanager](parkmanager) page).

PM is also responsible for issuing commands to the individual attraction services.

## Attraction Overview
AttractionAttributes stores all the static ride data (id, name, # vehicles, passengers/hr, etc). This is accessed at [/attributes](sample/attributes.json)

AttractionStatus stores all the live ride information (array of Vehicles, status message, passengers in line, etc). This is accessed at [/status](sample/status.json)


The core of every attraction is the singleton AbstractAttractionDriver class. This is responsible for basically everything in the attraction - it instantiates AttractionAttributes from flatfile, stores AttractionStatus, and implements all of the ride simulation mechanics. Since we don't have actual ride sensors to gather data from, all of the ride data is simulated in this class.

Different ride types (ex: roller coaster vs omnimover) simply need to extend AbstractApplicationDriver and can be called upon by the RestController.

#### Implementing AbstractApplicationDriver
The key functions of AAD are:
- setup(): This is called when the ride first starts up. This would typically prepare some/all ride vehicles and start loading the first one.

- tick(): This code is repeatedly run (every few seconds) to simulate a significant amount of time passing. This should be used to load the next ride vehicle and simulate random faults. (Note: when a fault occurs, ticksPaused should be set to TRUE so that the ride is "frozen" until someone resolves the fault)

- dismissFault(): This is called to acknowledge a fault and move the vehicle out of service so the ride can continue operating

- resolveFaults(): This is called to "fix" all OUT_OF_ORDER vehicles and return them to storage with the other vehicles that are not currently running on the track

- addVehicle(): Moves a vehicle from storage to the track

## Key Technologies
This system is made possible thanks to the following technologies:
- [Spring Boot](https://github.com/spring-projects/spring-boot) providing REST APIs between the attractions and the ParkManager, and between the ParkManager and client
- [GraphQL](https://github.com/graphql) allowing clients to query specific information from the ParkManager
- [Netflix Eureka](https://github.com/Netflix/eureka) allows attractions to register with the ParkManager without having to worry about manually configuring attraction URLs
- [Docker](https://github.com/docker) allows for containerizing all the attractions and ParkManager

## Running the test environment
Run [deploy-and-run.sh](deploy-and-run.sh) to automatically build all of the projects, create Docker containers, and start the ParkManager instance as well as three rollercoaster instances. The ParkManager can then be accessed at `http://localhost:8080/` and attractions can be accessed at `http://localhost:8080/attractions`

**Note:** it may take a few moments for ParkManager to discover the attractions once everything is started.