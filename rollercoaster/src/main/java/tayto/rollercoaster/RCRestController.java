package tayto.rollercoaster;

import org.springframework.web.bind.annotation.*;
import tayto.core.*;

import java.util.ArrayList;

@RestController
public class RCRestController {

    // Get static attraction data
    @RequestMapping(value="/attributes",method= RequestMethod.GET)
    public AttractionAttributes getAttributes() {
        return RCDriver.getInstance().attributes;
    }

    // Get live attraction status
    @RequestMapping(value="/status",method= RequestMethod.GET)
    public AttractionStatus getStatus() {
        return RCDriver.getInstance().status;
    }

    // Handle incoming actions
    @RequestMapping(value="/action/{action}",method=RequestMethod.POST)
    public String getResource(@PathVariable("action") String action) {
        if (action.equalsIgnoreCase("dismissfault")) {
            RCDriver.getInstance().dismissFault();
            return "Faulty vehicles have been moved to storage";
        } else if (action.equalsIgnoreCase("resolvefaults")) {
            RCDriver.getInstance().resolveFaults();
            return "All faulty vehicles have been fixed";
        } else if (action.equalsIgnoreCase("addvehicle")) {
            RCDriver.getInstance().addVehicle();
            return "An available stored vehicle has been added";
        }
        return "Unknown request. Valid commands are: dismissfault, resolvefaults, addvehicle. You requested: [" + action + "]";
    }
}
