package com.example.GameData.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "game_stats")
public class GameStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //ManyToOne för att samma spelare ska kunna finnas i databasen under flera matcher
    //Fält för att lagra matchen, ID, datum med mera.
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    private String gameId;
    private LocalDateTime gameDate;
    private int goals;
    private int assists;
    private int pim;

    //För att sätta gamedate till nuvarande tidpunkt
    public GameStats() {
        this.gameDate = LocalDateTime.now();
    }
    // Getters och setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public LocalDateTime getGameDate() {
        return gameDate;
    }
    public void setGameDate(LocalDateTime gameDate) {
        this.gameDate = gameDate;
    }
    public int getGoals() {
        return goals;
    }
    public void setGoals(int goals) {
        this.goals = goals;
    }
    public int getAssists() {
        return assists;
    }
    public void setAssists(int assists) {
        this.assists = assists;
    }
    public int getPim() {
        return pim;
    }
    public void setPim(int pim) {
        this.pim = pim;
    }
    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}