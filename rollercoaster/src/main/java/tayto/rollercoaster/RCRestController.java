package tayto.rollercoaster;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tayto.core.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RCRestController {

    // Get static attraction data
    @RequestMapping(value="/attributes",method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AttractionAttributes getAttributes() {
        return RCDriver.getInstance().attributes;
    }

    // Get live attraction status
    @RequestMapping(value="/status",method= RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AttractionStatus getStatus() {
        return RCDriver.getInstance().status;
    }

    // Handle incoming actions
    @RequestMapping(value="/action/{action}",method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getResource(@PathVariable("action") String action) {
        if (action.equalsIgnoreCase("dismissfault")) {
            RCDriver.getInstance().dismissFault();
            return "{message: \"success\"}";
        } else if (action.equalsIgnoreCase("resolvefaults")) {
            RCDriver.getInstance().resolveFaults();
            return "{message: \"success\"}";
        } else if (action.equalsIgnoreCase("addvehicle")) {
            RCDriver.getInstance().addVehicle();
            return "{message: \"success\"}";
        }
        return "{message: \"Unknown request. Valid commands are: dismissfault, resolvefaults, addvehicle. You requested: [" + action + "]\"}";
    }
}
