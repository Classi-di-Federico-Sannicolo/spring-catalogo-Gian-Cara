package itt.marconi.catalogocanzoni.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Canzone {
    @Id
@GeneratedValue(strategy = GenerationType.UUID) // Genera automaticamente una stringa univoca

    private String codice; 
    private String titolo;
    private String autore;
    private Integer anno;

    @ElementCollection 
    private List<String> categorie;
}