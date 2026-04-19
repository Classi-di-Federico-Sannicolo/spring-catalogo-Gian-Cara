package itt.marconi.catalogocanzoni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Configura automaticamente l'ambiente Spring e il server web
public class CatalogocanzoniApplication {

    public static void main(String[] args) {
        // Avvia l'applicazione Spring Boot
        SpringApplication.run(CatalogocanzoniApplication.class, args);
    }

}