package pl.jsolve.goldenlink.config
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan('pl.jsolve.goldenlink')
class Application {

    static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
