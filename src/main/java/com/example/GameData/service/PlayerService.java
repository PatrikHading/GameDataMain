package com.example.GameData.service;

import com.example.GameData.model.Player;
import com.example.GameData.repository.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void savePlayer(Player player) {
        Player savedPlayer = playerRepository.save(player);
        System.out.println("Player saved: " + savedPlayer);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    public Player getPlayerById(Integer id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
    }

    public void deletePlayer(Integer id) {
        if (playerRepository.existsById(id)) {
            playerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Player not found with id: " + id);
        }
    }
}
