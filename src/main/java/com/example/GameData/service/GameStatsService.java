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

    @Autowired
    private GameStatsRepository gameStatsRepository;

    public void saveGameStats(GameStats stats) {
        gameStatsRepository.save(stats);
    }

    public List<GameStats> getPlayerStats(Player player) {
        return gameStatsRepository.findByPlayer(player);
    }

    public List<GameStats> getStatsByDateRange(LocalDateTime start, LocalDateTime end) {
        return gameStatsRepository.findByGameDateBetween(start, end);
    }

    public Integer getPlayerTotalGoals(Long playerId) {
        return gameStatsRepository.getTotalGoalsByPlayerId(playerId);
    }

    public Integer getPlayerTotalAssists(Long playerId) {
        return gameStatsRepository.getTotalAssistsByPlayerId(playerId);
    }

    public Integer getPlayerTotalPim(Long playerId) {
        return gameStatsRepository.getTotalPimByPlayerId(playerId);
    }

    public List<String> findUniqueGameIds() {
        return gameStatsRepository.findDistinctGameIds();
    }

    public List<GameStats> findByGameId(String gameId) {
        return gameStatsRepository.findByGameId(gameId);
    }
}