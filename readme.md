# Distributed Theme Park Management System

## System Overview
This system is composed of two main components: several independent attractions and one management server. Each attraction does not save any state and only knows it's current operating status. The management server polls every attraction frequently to collect information and generate conclusions from it.

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

