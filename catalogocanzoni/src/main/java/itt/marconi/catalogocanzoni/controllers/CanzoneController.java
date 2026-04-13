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

    @GetMapping
    public String index(@RequestParam(value="search", required=false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("canzoni", repo.findByTitoloContainingIgnoreCase(search));
        } else {
            model.addAttribute("canzoni", repo.findAll());
        }
        return "index";
    }

    @GetMapping("/new")
    public String nuovo(Model model) {
        model.addAttribute("canzone", new Canzone());
        
        // Lista completa di categorie musicali
        List<String> categorie = Arrays.asList(
            "Pop", "Rock", "Trap", "Rap", "Indie", "R&B", "Jazz", "Blues", 
            "Elettronica", "Reggaeton", "Metal", "Classica", "Country", "Punk", 
            "Soul", "Funk", "Disco", "Folk", "Gospel", "Lo-fi"
        );
        model.addAttribute("generi", categorie);
        return "form";
    }

    @PostMapping("/save")
    public String salva(@ModelAttribute Canzone c, RedirectAttributes ra) {
        // Il codice viene generato automaticamente grazie all'annotazione @GeneratedValue(strategy = GenerationType.UUID) nel modello
        repo.save(c);
        ra.addFlashAttribute("messaggio", "Canzone salvata correttamente!");
        return "redirect:/item/" + c.getCodice();
    }

    @GetMapping("/item/{id}")
    public String dettaglio(@PathVariable String id, Model model) {
        model.addAttribute("canzone", repo.findById(id).orElse(null));
        return "dettaglio";
    }

    @GetMapping("/delete/{id}")
    public String elimina(@PathVariable String id) {
        repo.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/clear")
    public String svuota() {
        repo.deleteAll();
        return "redirect:/";
    }
}