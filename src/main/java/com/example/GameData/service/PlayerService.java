package com.example.GameData.service;

import com.example.GameData.model.Player;
import com.example.GameData.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    //Injecerar PlayerRepository
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Metod för att spara en ny spelare i databasen
    public void savePlayer(Player player) {
        Player savedPlayer = playerRepository.save(player);
        System.out.println("Player saved: " + savedPlayer);
    }

    // Metod för att uppdatera en existerande spelare med nya uppgifter
    public void updatePlayer(Integer id, Player updatedPlayer) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        player.setName(updatedPlayer.getName());
        player.setLastname(updatedPlayer.getLastname());
        player.setJerseynumber(updatedPlayer.getJerseynumber());

        playerRepository.save(player);
    }

    // Metod för att hämta alla spelare från databasen
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Metod för att hämta en specifik spelare baserat på id
    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    // Metod för att ta bort en spelare från databasen
    public void deletePlayer(Integer id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Player not found with id: " + id);
        }
    }
}
