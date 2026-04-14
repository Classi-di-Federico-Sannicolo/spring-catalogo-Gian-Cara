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

    @Autowired
    private CanzoneRepository repo;

    // Lista completa delle categorie centralizzata
    private final List<String> tutteLeCategorie = Arrays.asList(
        "Pop", "Rock", "Trap", "Rap", "Indie", "R&B", "Jazz", "Blues", 
        "Elettronica", "Reggaeton", "Metal", "Classica", "Country", "Punk", 
        "Soul", "Funk", "Disco", "Folk", "Gospel", "Lo-fi"
    );

    // Pagina principale con ricerca
    @GetMapping
    public String index(@RequestParam(value="search", required=false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("canzoni", repo.findByTitoloContainingIgnoreCase(search));
        } else {
            model.addAttribute("canzoni", repo.findAll());
        }
        return "index";
    }

    // Form per nuova canzone
    @GetMapping("/new")
    public String nuovo(Model model) {
        model.addAttribute("canzone", new Canzone());
        model.addAttribute("generi", tutteLeCategorie);
        return "form";
    }

    // Form per modifica canzone esistente
    @GetMapping("/edit/{id}")
    public String modifica(@PathVariable String id, Model model) {
        Canzone canzone = repo.findById(id).orElse(null);
        if (canzone == null) return "redirect:/";
        
        model.addAttribute("canzone", canzone);
        model.addAttribute("generi", tutteLeCategorie);
        return "form";
    }

    // Salvataggio (Gestisce sia inserimento che modifica)
    @PostMapping("/save")
    public String salva(@ModelAttribute Canzone c, RedirectAttributes ra) {
        /* * FIX ERRORE 500: Se il codice è una stringa vuota (""), Hibernate pensa che sia 
         * un ID esistente e va in errore. Settandolo a null, forziamo la creazione di un nuovo UUID.
         */
        if (c.getCodice() != null && c.getCodice().isEmpty()) {
            c.setCodice(null);
        }

        repo.save(c);
        ra.addFlashAttribute("messaggio", "Operazione completata con successo!");
        return "redirect:/item/" + c.getCodice();
    }

    // Dettaglio canzone
    @GetMapping("/item/{id}")
    public String dettaglio(@PathVariable String id, Model model) {
        Canzone c = repo.findById(id).orElse(null);
        if (c == null) return "redirect:/";
        model.addAttribute("canzone", c);
        return "dettaglio";
    }

    // Elimina singola canzone
    @GetMapping("/delete/{id}")
    public String elimina(@PathVariable String id) {
        repo.deleteById(id);
        return "redirect:/";
    }

    // Svuota intero catalogo
    @GetMapping("/clear")
    public String svuota() {
        repo.deleteAll();
        return "redirect:/";
    }
}