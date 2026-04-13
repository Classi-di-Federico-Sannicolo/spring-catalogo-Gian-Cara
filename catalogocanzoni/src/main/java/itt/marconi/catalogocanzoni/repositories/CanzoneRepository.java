package itt.marconi.catalogocanzoni.repositories;

import itt.marconi.catalogocanzoni.models.Canzone;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CanzoneRepository extends JpaRepository<Canzone, String> {
    
    List<Canzone> findByTitoloContainingIgnoreCase(String titolo);
}