package itt.marconi.catalogocanzoni.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Canzone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private String codice; 

    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotBlank(message = "L'autore è obbligatorio")
    private String autore;

    @Min(value = 1000, message = "Anno non valido")
    @Max(value = 2099, message = "Anno troppo lontano")
    private int anno;

    @ElementCollection 
    private List<String> categorie = new ArrayList<>();

    // COSTRUTTORI
    public Canzone() {}

    public Canzone(String codice, String titolo, String autore, Integer anno, List<String> categorie) {
        this.codice = codice;
        this.titolo = titolo;
        this.autore = autore;
        this.anno = anno;
        this.categorie = categorie;
    }

    // GETTER E SETTER (Indispensabili per Thymeleaf)
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