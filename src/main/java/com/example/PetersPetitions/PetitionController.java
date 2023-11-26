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

    /**
     * home page Control method - displays the home page
     * @param req
     * @param model
     * @return
     */
    @GetMapping("/")
    public String home(HttpServletRequest req, Model model) {
        Petition.makePetitions();
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "Home");
        model.addAttribute("petitions", Petition.getAllPetitions());
        return "index";
    }

    /**
     * view page. Displays all petitions
     * @param model
     * @param req
     * @return
     */
    @GetMapping(value = "/view")
    public String view(Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "View All Petitions");
        model.addAttribute("petitions", Petition.getAllPetitions());
        return "view";
    }

    /**
     * view/{pttn} page (i.e. view/some_petition_i01) displays the petition with the supplied uniqueTitle
     * @param model
     * @param pttn
     * @param req
     * @return
     */
    @GetMapping(value = "/view/{pttn}")
    public String petition(Model model, @PathVariable String pttn, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
        List<Petition> somePetitions = Petition.getAllPetitions().stream().filter(pt -> pt.getUniqueTitle().equalsIgnoreCase(pttn)).toList();
        if (somePetitions.isEmpty())
            return "404";
        Petition thePetition = somePetitions.get(0);
        model.addAttribute("petition", thePetition);
        model.addAttribute("uTitle", pttn);
        model.addAttribute("title", thePetition.getTitle());
        return "view-petition";
    }

    /**
     * /view/{pttn}/add Adds a signature (information provided in POST request) to the petition defined by the
     * uniqueTitle supplied in {pttn}. Redirects back to /view/{pttn} page
     * @param name
     * @param email
     * @param pttn
     * @return
     */
    @PostMapping(value = "/view/{pttn}/add")
    public String addSignature(@RequestParam("name") String name, @RequestParam("email") String email, @PathVariable String pttn){
        List<Petition> somePetitions = Petition.getAllPetitions().stream().filter(pt -> pt.getUniqueTitle().equalsIgnoreCase(pttn)).toList();
        if (somePetitions.isEmpty()) return "404";
        Petition p = somePetitions.get(0);
        p.addSignatory(name, email);
        return "redirect:../../view/"+pttn;
    }

    /**
     * displays the create petition form
     * @param model
     * @param req
     * @return
     */
    @GetMapping(value = "/create")
    public String create(Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "Create Petition");
        return "create";
    }

    /**
     * submitts the create petition form. Title, description, name and email supplied in POST request
     * @param title
     * @param description
     * @param name
     * @param email
     * @param model
     * @return
     */
    @PostMapping(value = "/create/new")
    public String createPetition(@RequestParam("title") String title,
                                 @RequestParam("description") String description,
                                 @RequestParam("authorName") String name,
                                 @RequestParam("authorEmail") String email,
                                 Model model) {

        User author = new User(name, email);
        Petition petition = new Petition(title, description, author);

        return "redirect:../view/"+petition.getUniqueTitle();
    }

    /**
     * displays the search page
     * @param model
     * @param req
     * @return
     */
    @GetMapping(value = "/search")
    public String search(Model model, HttpServletRequest req){
        model.addAttribute("depth", generateHierarchy(req));
        model.addAttribute("title", "Search Petitions");
        return "search";
    }

    /**
     * displays the search result from the search string supplied in POST request
     * @param searchValue
     * @param model
     * @param req
     * @return
     */
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
        model.addAttribute("title", "Search Results");
        model.addAttribute("petitions", weightedPetitionsArray);
        return "search-result";
    }

    /**
     * redirects /page/ pages to their non / suffixed variations
     * @param page
     * @param req
     * @return
     */
    @GetMapping(value = "/{page}/")
    public String redirectView(@PathVariable String page, HttpServletRequest req){
        return "redirect:" + generateHierarchy(req) + "/" +  page;
    }

    /**
     * * redirects /page1/page2/ pages to their non / suffixed variations
     * @param page1
     * @param page2
     * @param req
     * @return
     */
    @GetMapping(value = "/{page1}/{page2}/")
    public String redirectView2(@PathVariable String page1,@PathVariable String page2, HttpServletRequest req){
        return "redirect:" + generateHierarchy(req) + "/" +  page1 + "/" + page2;
    }

}
