package com.example.PetersPetitions;

import java.util.ArrayList;

public class Petition {
    /**
     * a list of all the petitions
     */
    private static ArrayList<Petition> petitions = new ArrayList<>();

    public static ArrayList<Petition> getPetitions(){
        return petitions;
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
     * Constructor
     * @param title
     * @param description
     * @param signatures
     * @param author
     */
    public Petition(String title, String description, ArrayList<User> signatures, User author) {
        this.title = title;
        this.description = description;
        this.signatures = signatures;
        this.author = author;
    }


    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
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
