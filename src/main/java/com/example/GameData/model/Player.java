package com.example.GameData.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @Min(value = 1, message = "Jersey number must be at least 1")
    @Max(value = 100, message = "Jersey number cannot be greater than 100")
    private int jerseynumber;

    public Player() {
    }

    public Player(String name, String lastname, int jerseynumber) {
        this.name = name;
        this.lastname = lastname;
        this.jerseynumber = jerseynumber;
    }

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