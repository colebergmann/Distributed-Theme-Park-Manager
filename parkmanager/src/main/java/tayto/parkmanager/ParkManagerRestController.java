package tayto.parkmanager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tayto.core.AttractionDetails;

@RestController
public class ParkManagerRestController {
    @RequestMapping(value="/test", method= RequestMethod.GET)
    public AttractionDetails test() {
        return ManagerDriver.getInstance().attractions.get("SPACEMTN").getDetails();
    }
}
