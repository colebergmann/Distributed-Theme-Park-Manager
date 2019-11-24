package tayto.rollercoaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        System.out.println("Arg: " + args[0]);
        new RCDriver(args[0]);
        SpringApplication.run(Application.class, args);
    }
}