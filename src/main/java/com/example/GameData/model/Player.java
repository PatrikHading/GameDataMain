package com.example.GameData.model;

import jakarta.persistence.*;

@Entity
public class Player {

    //Id och GeneratedValue för att automatisk generera ID på spelare som sparas i databasen.
    //Fält för att lagra spelare namn efternamn tröjnummer
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String lastname;
    private int jerseynumber;

    //Standardkonstruktor
    public Player() {
    }

    //Konstruktor för att skapa spelare med namn, efternamn och tröjnummer
    public Player(String name, String lastname, int jerseynumber) {
        this.name = name;
        this.lastname = lastname;
        this.jerseynumber = jerseynumber;
    }

    //Getters och setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public int getJerseynumber() {
        return jerseynumber;
    }
    public void setJerseynumber(int jerseynumber) {
        this.jerseynumber = jerseynumber;
    }
}
