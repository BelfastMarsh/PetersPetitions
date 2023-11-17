package com.example.PetersPetitions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        model.addAttribute("title", "Petition - " + name);

        return "index";
    }

    @GetMapping(value = "/create")
    public String create(Model model){
        model.addAttribute("title", "Create Petition");
        return "index";
    }

    @GetMapping(value = "/search")
    public String search(Model model){
        model.addAttribute("title", "Search Petitions");
        return "index";
    }

}
