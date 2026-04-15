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

    private final List<String> tutteLeCategorie = Arrays.asList(
        "Pop", "Rock", "Trap", "Rap", "Indie", "R&B", "Jazz", "Blues", 
        "Elettronica", "Reggaeton", "Metal", "Classica", "Country", "Punk", 
        "Soul", "Funk", "Disco", "Folk", "Gospel", "Lo-fi"
    );

    @GetMapping
    public String index(
            @RequestParam(value="search", required=false) String search,
            @RequestParam(value="anno", required=false) Integer anno,
            @RequestParam(value="genere", required=false) String genere,
            Model model) {
        
        String testoSearch = (search != null && !search.trim().isEmpty()) ? search : null;
        String genereSearch = (genere != null && !genere.trim().isEmpty()) ? genere : null;
        
        model.addAttribute("canzoni", repo.searchCanzoni(testoSearch, anno, genereSearch));
        model.addAttribute("generi", tutteLeCategorie);
        
        return "index";
    }

    @GetMapping("/new")
    public String nuovo(Model model) {
        model.addAttribute("canzone", new Canzone());
        model.addAttribute("generi", tutteLeCategorie);
        return "form";
    }

    @GetMapping("/edit/{id}")
    public String modifica(@PathVariable String id, Model model) {
        Canzone canzone = repo.findById(id).orElse(null);
        if (canzone == null) return "redirect:/";
        model.addAttribute("canzone", canzone);
        model.addAttribute("generi", tutteLeCategorie);
        return "form";
    }

    @PostMapping("/save")
    public String salva(@ModelAttribute Canzone c, RedirectAttributes ra) {
        if (c.getCodice() != null && c.getCodice().isEmpty()) {
            c.setCodice(null);
        }
        repo.save(c);
        ra.addFlashAttribute("messaggio", "Salvataggio completato!");
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