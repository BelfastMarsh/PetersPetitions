package com.example.PetersPetitions;

import java.util.HashMap;
import java.util.List;

public class WeightedPetition {


    /**
     * Creates a HashMap of String & WeightedPetition objects for displaying results from search in some form
     * of order relating to how similar the title of the petition is to the value searched by the user.
     * @param searchValue the value searched by the user
     * @return a HashMap&lt;String, WeightedPetition&gt; which lists all the petitions which match the search term and
     * their weighting. The String is the uniqueTitle of the petition.
     */
    public static HashMap<String, WeightedPetition> createWeightedPetitions(String searchValue){
        String[] bits = searchValue.split(" ");
        HashMap<String, WeightedPetition> weightedPetitions = new HashMap<>();


        // this is the mad weighting algorithm I came up with
        /*
         add 1 to weighting if title contains word as part word
         add 3 to weighting if title contains word as full word
         add 10 to weighting if title == complete search term
         add 5 to weighting if title contains complete search term.
        */
        for (String bit : bits){
            List<Petition> matchingPetitions =
                    Petition.getAllPetitions().stream().filter(p ->
                            p.getTitle().toLowerCase().contains(bit.toLowerCase())).toList();
            for (Petition petition : matchingPetitions){
                if (!weightedPetitions.containsKey(petition.getUniqueTitle())){
                    weightedPetitions.put(petition.getUniqueTitle(),
                            new WeightedPetition(0,petition));
                }
                if (weightedPetitions.get(petition.getUniqueTitle()).getPetition().getTitle(" ").
                        toLowerCase().contains(" " + bit.toLowerCase() + " ")){
                    weightedPetitions.get(petition.getUniqueTitle()).addWeighting(3);
                }
                else {
                    weightedPetitions.get(petition.getUniqueTitle()).addWeighting(1);
                }
            }
        }
        // add additional weighting if search term == title of petition
        // or add (not quite as much) weighting if search term is a substring of title
        for (WeightedPetition weightedPetition : weightedPetitions.values()) {
            if (weightedPetition.getPetition().getTitle().equalsIgnoreCase(searchValue))
                weightedPetition.addWeighting(10);
            else if (weightedPetition.getPetition().getTitle().toLowerCase().
                    contains(searchValue.toLowerCase()))
                weightedPetition.addWeighting(5);
        }

        return weightedPetitions;

    }




    private int weighting;
    private Petition petition;
    public WeightedPetition(int weighting, Petition petition) {
        this.petition = petition;
        this.weighting = weighting;
    }

    public Petition getPetition() {
        return petition;
    }

    public int getWeighting(){
        return weighting;
    }
    public void addWeighting(int weighting){
        this.weighting += weighting;
    }
}
