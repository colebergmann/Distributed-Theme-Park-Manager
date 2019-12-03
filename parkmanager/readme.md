# Park Manager Service
The Park Manager Service is responsible for all communication to the attractions. Clients should not interact directly with the attractions and should instead interface with the PM.

Services are automatically discovered with Netflix Eureka.

### Web Interface
The Park Manager Service also hosts a web interface that park administrators and park guests can use to view the attraction statuses. Read more at [webinterface.md](webinterface.md)

## REST Operations
The following REST endpoints are supported:
- GET /attractions: returns a VERY DETAILED list of AttractionDetails objects. Use GraphQL queries so you have less data to deal with
- GET /attractions/{id}: returns a single AttractionDetails object for a corresponding ride id
- GET /attractions/{id}/action/{action}: executes an action on an attraction (dismissfault, resolvefaults, addvehicle) and logs it

## GraphQL Operations
GraphQL is a very efficient way to fetch data. You can specify exactly what fields should be returned, rather than returning a bunch of unnecessary data.
I highly recommend downloading [GraphQL Playground](https://github.com/prisma-labs/graphql-playground) to get a feel for how it works.

There are two functions: `attractions` and `attractionById(id: ID)`. Like the REST interface, attractions returns all AttractionDetails and attractionById returns a single AttractionDetails object.

**Note: The graphql endpoint is at /graphql**

Example query:
```
{
  attractions {
    attributes {
      attractionName
      attractionId
      rideDuration
      passengersPerCarPerHour
      passengersPerCar
      maxSpeed
      loops
      heightRequired
      loadTime
      vehicleCount
    }
    status{
      stage
      passengersInLine
      faultMessage
      rideVehicles{
        stage
      }
    }
    waitMins
    events
    suggestions
    vehicleFillRate
    vehiclesInService
    vehiclesInStorage
    totalBrokenVehicles
  }
}
```

(Example response at [../samples/attractions-full.json](../sample/attractions-full.json))

This obviously returns way too much data. You can delete any of the fields you don't need to return a much easier dataset:
```
{
  attractions {
    attributes {
      attractionName
      attractionId
      maxSpeed
      loops
      heightRequired
    }
    status{
      stage
      passengersInLine
      faultMessage
    }
    vehicleFillRate
  }
}
```
(Example response at [../samples/attractions-concise.json](../sample/attractions-concise.json))

### Querying on a single attraction
Use the exact same query format to query a single attraction, but replace the first line with `attractionById(id: <ID>)`
```
{
  attractionById(id: "SPACEMTN") {
    attributes {
      attractionName
...
```