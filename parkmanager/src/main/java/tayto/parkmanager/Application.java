package tayto.parkmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableEurekaServer
public class Application {
    public static void main(String[] args) {
        System.out.println("[ParkManager] Starting");
        SpringApplication.run(Application.class, args);

        // Initialize the ManagerDriver
        ManagerDriver.getInstance();
    }


    // Required so CORS errors aren't thrown for graphql
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/graphql").allowedOrigins("*");
                registry.addMapping("/attractions").allowedOrigins("*");
            }
        };
    }
}
