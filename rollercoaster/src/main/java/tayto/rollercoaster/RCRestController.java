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
    @RequestMapping(value="/action/{action}",method=RequestMethod.GET)
    public String getResource(@PathVariable("action") String action) {
        if (action.equalsIgnoreCase("dismissfault")) {
            RCDriver.getInstance().dismissFault();
            return "success";
        } else if (action.equalsIgnoreCase("resolvefaults")) {
            RCDriver.getInstance().resolveFaults();
            return "success";
        } else if (action.equalsIgnoreCase("addvehicle")) {
            RCDriver.getInstance().addVehicle();
            return "success";
        }
        return "Unknown request. Valid commands are: dismissfault, resolvefaults, addvehicle. You requested: [" + action + "]";
    }
}
