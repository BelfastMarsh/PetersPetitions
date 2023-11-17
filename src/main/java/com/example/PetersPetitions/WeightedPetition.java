package com.example.PetersPetitions;

public class WeightedPetition {
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
