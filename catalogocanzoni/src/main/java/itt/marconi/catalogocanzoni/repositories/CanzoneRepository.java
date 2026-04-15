package itt.marconi.catalogocanzoni.repositories;

import itt.marconi.catalogocanzoni.models.Canzone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CanzoneRepository extends JpaRepository<Canzone, String> {

    @Query("SELECT c FROM Canzone c WHERE " +
           "(:testo IS NULL OR LOWER(c.titolo) LIKE LOWER(CONCAT('%', :testo, '%')) " +
           "OR LOWER(c.autore) LIKE LOWER(CONCAT('%', :testo, '%'))) AND " +
           "(:anno IS NULL OR c.anno = :anno) AND " +
           "(:genere IS NULL OR :genere MEMBER OF c.categorie)")
    List<Canzone> searchCanzoni(
        @Param("testo") String testo, 
        @Param("anno") Integer anno, 
        @Param("genere") String genere
    );
}