package ma.zs.generator;

import java.io.IOException;
import ma.zs.generator.business.projet.ProjetBusinessEngineImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeneratorApplication {

    public static void main(String[] args) throws IOException, Exception {
        SpringApplication.run(GeneratorApplication.class, args);
       
        
    }

}
