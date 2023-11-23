package com.example.PetersPetitions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class PetitionController {

    /**
     * method to calculate the number of ../ is needed to return to navigate successfully around the
     * @param req HttpServletRequest
     * @return String of for form "[..[/..](cont.)]"
     */
    private String generateHierarchy(HttpServletRequest req){
        String url = req.getRequestURI();
        url = url.replace("/PetersPetitions/", "");
        int urlDepth = url.split("/").length -2 ;
        String hierarchy = "";
        String slsh = "";
        for(int d = 0; d < urlDepth; d++){
            hierarchy += slsh + "..";
            slsh = "/";
        }
        return hierarchy;
    }


    @GetMapping("/")
    public String home(HttpServletRequest req, Model model) {
        Petition.makePetitions();
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "View All Petitions");
        model.addAttribute("petitions", Petition.getAllPetitions());
        return "index";
    }


    @GetMapping(value = "/view")
    public String view(Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "View All Petitions");
        model.addAttribute("petitions", Petition.getAllPetitions());
        return "view";
    }


    @GetMapping(value = "/view/{name}")
    public String petition(Model model, @PathVariable String name, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
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
        return "redirect:../../view/"+pttn;
    }


    @GetMapping(value = "/create")
    public String create(Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
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

        return "redirect:../view/"+petition.getUniqueTitle();
    }


    @GetMapping(value = "/search")
    public String search(Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "Search Petitions");
        return "search";
    }


    @PostMapping(value = "/search/result")
    public String searchResult(@RequestParam("search-text") String searchValue,
                               Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));

        // get weighted petitions from search term.
        HashMap<String, WeightedPetition> weightedPetitions = WeightedPetition.createWeightedPetitions(searchValue);

        //sort weighted petitions in reverse order of weighting
        List<WeightedPetition> weightedPetitionsArray =
                weightedPetitions.values().stream().
                        sorted(Comparator.comparingInt(WeightedPetition::getWeighting).reversed()).toList();

        model.addAttribute("petitions", weightedPetitionsArray);
        return "search-result";
    }


    // redirect /page/ and /page/subpage/ to their non / suffexed variations
    @GetMapping(value = "/{page}/")
    public String redirectView(@PathVariable String page, HttpServletRequest req){
        return "redirect:" + generateHierarchy(req) + "/" +  page;
    }


    @GetMapping(value = "/{page1}/{page2}/")
    public String redirectView2(@PathVariable String page1,@PathVariable String page2, HttpServletRequest req){
        return "redirect:" + generateHierarchy(req) + "/" +  page1 + "/" + page2;
    }

}
