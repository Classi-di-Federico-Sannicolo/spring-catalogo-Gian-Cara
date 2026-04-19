package itt.marconi.catalogocanzoni.controllers;

import itt.marconi.catalogocanzoni.models.Canzone;
import itt.marconi.catalogocanzoni.repositories.CanzoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class CanzoneController {

    // Iniezione del repository per gestire le operazioni sul database MySQL
    @Autowired
    private CanzoneRepository repo;

    // Lista statica per popolare i menu a tendina nel form
    private final List<String> tutteLeCategorie = Arrays.asList(
        "Pop", "Rock", "Trap", "Rap", "Indie", "R&B", "Jazz", "Blues", 
        "Elettronica", "Reggaeton", "Metal", "Classica", "Country", "Punk", 
        "Soul", "Funk", "Disco", "Folk", "Gospel", "Lo-fi"
    );

    /**
     * Pagina principale: mostra la tabella con supporto ai filtri di ricerca.
     */
    @GetMapping
    public String index(
            @RequestParam(value="search", required=false) String search,
            @RequestParam(value="anno", required=false) Integer anno,
            @RequestParam(value="genere", required=false) String genere,
            Model model) {
        
        // Pulizia dei parametri: se sono stringhe vuote o spazi, passiamo null al database
        String testoSearch = (search != null && !search.trim().isEmpty()) ? search : null;
        String genereSearch = (genere != null && !genere.trim().isEmpty()) ? genere : null;
        
        // Recupera i dati filtrati e li passa alla vista index.html
        model.addAttribute("canzoni", repo.searchCanzoni(testoSearch, anno, genereSearch));
        model.addAttribute("generi", tutteLeCategorie);
        
        return "index";
    }

    /**
     * Prepara il form per l'inserimento di una nuova canzone.
     */
    @GetMapping("/new")
    public String nuovo(Model model) {
        model.addAttribute("canzone", new Canzone()); // Oggetto vuoto per il form
        model.addAttribute("generi", tutteLeCategorie);
        return "form";
    }

    /**
     * Carica i dati di una canzone esistente per la modifica.
     */
    @GetMapping("/edit/{id}")
    public String modifica(@PathVariable String id, Model model) {
        Canzone canzone = repo.findById(id).orElse(null);
        if (canzone == null) return "redirect:/"; // Se l'ID non esiste, torna alla home
        
        model.addAttribute("canzone", canzone);
        model.addAttribute("generi", tutteLeCategorie);
        return "form"; // Riutilizza lo stesso template del "nuovo"
    }

    /**
     * Salva i dati nel database (sia per nuovi inserimenti che per modifiche).
     */
    @PostMapping("/save")
    public String salva(@ModelAttribute Canzone c, RedirectAttributes ra) {
        // Se il codice è una stringa vuota, lo impostiamo a null per far generare l'UUID al database
        if (c.getCodice() != null && c.getCodice().isEmpty()) {
            c.setCodice(null);
        }
        
        repo.save(c); // JPA esegue INSERT se l'ID è nullo, UPDATE se l'ID esiste già
        
        // Messaggio "usa e getta" che appare solo al prossimo caricamento pagina
        ra.addFlashAttribute("messaggio", "Salvataggio completato!");
        
        // Dopo il salvataggio, mostra la pagina di riepilogo specifica
        return "redirect:/item/" + c.getCodice();
    }

    /**
     * Mostra la pagina di dettaglio di una singola canzone (riepilogo).
     */
    @GetMapping("/item/{id}")
    public String dettaglio(@PathVariable String id, Model model) {
        model.addAttribute("canzone", repo.findById(id).orElse(null));
        return "dettaglio";
    }

    /**
     * Elimina un elemento specifico tramite il suo ID.
     */
    @GetMapping("/delete/{id}")
    public String elimina(@PathVariable String id) {
        repo.deleteById(id);
        return "redirect:/";
    }

    /**
     * Svuota l'intero catalogo.
     */
    @GetMapping("/clear")
    public String svuota() {
        repo.deleteAll();
        return "redirect:/";
    }
}   