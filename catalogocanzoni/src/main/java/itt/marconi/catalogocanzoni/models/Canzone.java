package itt.marconi.catalogocanzoni.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

// Indica che questa classe è un'entità gestita da JPA (corrisponde a una tabella nel DB)
@Entity
public class Canzone {
    
    // Identificativo univoco (Chiave Primaria)
    @Id
    // Genera automaticamente un codice univoco nel formato UUID (stringa casuale protetta da duplicati)
    @GeneratedValue(strategy = GenerationType.UUID) 
    private String codice; 

    // Validazione: impedisce che il campo sia vuoto o contenga solo spazi
    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message = "L'autore è obbligatorio")
    private String autore;

    // Validazione numerica: definisce un range temporale accettabile
    @Min(value = 1000, message = "Anno non valido")
    @Max(value = 2099, message = "Anno troppo lontano")
    private Integer anno;

    // Indica che questa lista verrà salvata in una tabella correlata (gestita automaticamente da JPA)
    @ElementCollection 
    private List<String> categorie = new ArrayList<>();

    // COSTRUTTORI
    
    // Costruttore vuoto obbligatorio per JPA
    public Canzone() {}

    // Costruttore completo per creare oggetti rapidamente via codice
    public Canzone(String codice, String titolo, String autore, Integer anno, List<String> categorie) {
        this.codice = codice;
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;
        this.categorie = categorie;
    }

    // GETTER E SETTER
    // Indispensabili affinché Thymeleaf possa leggere/scrivere i dati nei form HTML
    
    public String getCodice() { return codice; }
    public void setCodice(String codice) { this.codice = codice; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getAutore() { return autore; }
    public void setAutore(String autore) { this.autore = autore; }

    public Integer getAnno() { return anno; }
    public void setAnno(Integer anno) { this.anno = anno; }

    public List<String> getCategorie() { return categorie; }
    public void setCategorie(List<String> categorie) { this.categorie = categorie; }
}