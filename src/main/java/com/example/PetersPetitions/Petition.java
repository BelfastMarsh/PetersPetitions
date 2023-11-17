package com.example.PetersPetitions;

import java.util.ArrayList;

public class Petition {
    /**
     * a list of all the petitions
     */
    private static ArrayList<Petition> allPetitions = new ArrayList<>();

    private static boolean petitionsMade = false;

    // make placeholder petitions to test functionality
    public static void makePetitions(){

        if (!petitionsMade) {
            Petition p1 = new Petition("universal basic income", "everyone should have a universal basic income of €20,000"
                    , new User("bob", "bob@murphy.com"));
            Petition p2 = new Petition("8 day week", "Weeks should be 8 days so we can have a 3 day weekend and so the Beatles song makes sense"
                    , new User("George", "George@Harrison.be"));
        }
        petitionsMade = true;
    }

    public static ArrayList<Petition> getAllPetitions(){
        return allPetitions;
    }
    /**
     * the title of the petition
     */
    private String title;

    /**
     * the description of the petition
     */
    private String description;

    /**
     * the signatories of the petition
     */
    private ArrayList<User> signatures;

    /**
     * the author of the petition
     */
    private User author;

    /**
     * uniqueTitle for the petition for url routing
     */
    private String uniqueTitle;


    /**
     * Constructor
     * @param title title of the petition
     * @param description description of the petition
     * @param author Author (object) of the person who made the petition
     */
    public Petition(String title, String description, User author) {
        this.title = title;
        this.description = description;
        this.signatures = new ArrayList<>();
        this.author = author;

        // use streams to filter the titles to generate unique title
        int existingTitles = allPetitions.stream().filter(
                pt -> pt.uniqueTitle.equalsIgnoreCase(this.title.replace(' ','_'))).toArray().length;
        this.uniqueTitle = title.replace(' ','_') + "_i" + (++existingTitles);

        // add itself to the static reference list
        Petition.allPetitions.add(this);

    }


    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUniqueTitle() {
        return uniqueTitle;
    }

    public ArrayList<User> getSignatures() {
        return signatures;
    }

    public void setSignatures(ArrayList<User> signatures) {
        this.signatures = signatures;
    }

    public User getAuthor() {
        return author;
    }
}
