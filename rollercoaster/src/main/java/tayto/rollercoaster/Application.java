package tayto.rollercoaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: rollercoaster.jar config.yml");
            System.exit(1);
        }
        new RCDriver(args[0]);
        SpringApplication.run(Application.class, args);
    }
}