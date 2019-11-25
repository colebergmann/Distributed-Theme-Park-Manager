package tayto.parkmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;
import tayto.core.AttractionStatus;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("[ParkManager] Starting");
        //SpringApplication.run(Application.class, args);

        //AttractionEntry entry = new AttractionEntry("http://localhost:8000");
        ManagerDriver.getInstance().addAttraction("http://localhost:8000");
        ManagerDriver.getInstance().addAttraction("http://localhost:8001");
    }
}
