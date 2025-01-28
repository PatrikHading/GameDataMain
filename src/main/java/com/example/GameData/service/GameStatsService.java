package com.example.GameData.service;

import com.example.GameData.model.GameStats;
import com.example.GameData.model.Player;
import com.example.GameData.repository.GameStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GameStatsService {

    //Har flera metoder som inte används ännu, men som ska implementeras i framtiden.

    //Injicerar GameStatsRepository
    @Autowired
    private GameStatsRepository gameStatsRepository;

    // Metod för att spara statistik för ett spel
    public void saveGameStats(GameStats stats) {
        gameStatsRepository.save(stats);
    }

    // Metod för att hämta statistik för en specifik spelare
    public List<GameStats> getPlayerStats(Player player) {
        return gameStatsRepository.findByPlayer(player);
    }

    // Metod för att hämta statistik för en spelare baserat på ett dataintervall
    public List<GameStats> getStatsByDateRange(LocalDateTime start, LocalDateTime end) {
        return gameStatsRepository.findByGameDateBetween(start, end);
    }

    // Metod för att hämta den totala mängden mål för en spelare
    public Integer getPlayerTotalGoals(Long playerId) {
        return gameStatsRepository.getTotalGoalsByPlayerId(playerId);
    }

    // Metod för att hämta den totala mängden assists för en spelare
    public Integer getPlayerTotalAssists(Long playerId) {
        return gameStatsRepository.getTotalAssistsByPlayerId(playerId);
    }

    // Metod för att hämta den totala mängden PIM (penalty minutes) för en spelare
    public Integer getPlayerTotalPim(Long playerId) {
        return gameStatsRepository.getTotalPimByPlayerId(playerId);
    }

    // Metod för att hämta alla unika spel-ID:n
    public List<String> findUniqueGameIds() {
        return gameStatsRepository.findDistinctGameIds();
    }

    // Metod för att hämta statistik för en specifik match baserat på spel-ID
    public List<GameStats> findByGameId(String gameId) {
        return gameStatsRepository.findByGameId(gameId);
    }
}