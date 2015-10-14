package pl.jsolve.goldenlink.config
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@ComponentScan('pl.jsolve.goldenlink')
@EnableAspectJAutoProxy
class Application {

    static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
