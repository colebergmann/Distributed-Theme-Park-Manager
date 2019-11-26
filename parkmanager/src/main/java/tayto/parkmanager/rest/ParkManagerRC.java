package tayto.parkmanager.rest;

import org.springframework.web.bind.annotation.*;
import tayto.core.AttractionDetails;
import tayto.parkmanager.ManagerDriver;

import java.util.Collection;

@RestController
public class ParkManagerRC {
    @RequestMapping(value="/attractions", method= RequestMethod.GET)
    public Collection<AttractionDetails> getAllAttractions() {
        return ManagerDriver.getInstance().getAttractionDetails();
    }

    @RequestMapping(value="/attractions/{id}", method=RequestMethod.GET)
    public AttractionDetails getDetails(@PathVariable String id) {

        if (ManagerDriver.getInstance().attractions.containsKey(id)) {
            return ManagerDriver.getInstance().attractions.get(id).getDetails();
        }
        // id not found, throw an error
        throw new NoSuchAttractionException();
    }

    @RequestMapping(value="/attractions/{id}/action/{action}", method=RequestMethod.GET)
    public String executeAction(@PathVariable String id, @PathVariable String action) {

        if (!ManagerDriver.getInstance().attractions.containsKey(id)) {
            throw new NoSuchAttractionException();
        }

        return ManagerDriver.getInstance().attractions.get(id).performAction(action);
    }
}
