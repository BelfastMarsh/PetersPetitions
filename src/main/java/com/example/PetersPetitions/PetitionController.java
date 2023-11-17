package com.example.PetersPetitions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PetitionController {

    @GetMapping("/")
    public String home(Model model) {
        Petition.makePetitions();
        model.addAttribute("title", "View All Petitions");
        model.addAttribute("petitions", Petition.getAllPetitions());
        return "index";
    }

    @GetMapping(value = "/view")
    public String view(Model model){
        model.addAttribute("title", "View All Petitions");
        model.addAttribute("petitions", Petition.getAllPetitions());
        return "view";
    }

    @GetMapping(value = "/view/{name}")
    public String petition(Model model, @PathVariable String name){
        List<Petition> somePetitions = Petition.getAllPetitions().stream().filter(pt -> pt.getUniqueTitle().equalsIgnoreCase(name)).toList();
        if (somePetitions.isEmpty())
            return "404";
        Petition thePetition = somePetitions.get(0);
        model.addAttribute("petition", thePetition);
        model.addAttribute("uTitle", name);
        model.addAttribute("title", thePetition.getTitle());
        return "view-petition";
    }

    @PostMapping(value = "/view/{pttn}/add")
    public String addSignature(@RequestParam("name") String name, @RequestParam("email") String email, @PathVariable String pttn){
        List<Petition> somePetitions = Petition.getAllPetitions().stream().filter(pt -> pt.getUniqueTitle().equalsIgnoreCase(pttn)).toList();
        if (somePetitions.isEmpty()) return "404";
        Petition p = somePetitions.get(0);
        p.addSignatory(name, email);
        return "redirect:/view/"+pttn;
    }



    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("title", "Create Petition");
        return "create";
    }

    @PostMapping(value = "/create/new")
    public String createPetition(@RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("authorName") String name,
                                 @RequestParam("authorEmail") String email,
                                 Model model) {
        model.addAttribute("title", "Create Petition");
        User author = new User(name, email);
        Petition petition = new Petition(title, description, author);

        return "redirect:/view/"+petition.getUniqueTitle();
    }
    @GetMapping(value = "/search")
    public String search(Model model){
        model.addAttribute("title", "Search Petitions");
        return "search";
    }

}
