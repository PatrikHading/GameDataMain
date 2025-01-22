package com.example.GameData.repository;

import com.example.GameData.model.GameStats;
import com.example.GameData.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GameStatsRepository extends JpaRepository<GameStats, Long> {
    List<GameStats> findByPlayer(Player player);
    List<GameStats> findByGameDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT g FROM GameStats g WHERE g.player.id = :playerId")
    List<GameStats> findByPlayerId(@Param("playerId") Long playerId);

    @Query("SELECT SUM(g.goals) FROM GameStats g WHERE g.player.id = :playerId")
    Integer getTotalGoalsByPlayerId(@Param("playerId") Long playerId);

    @Query("SELECT SUM(g.assists) FROM GameStats g WHERE g.player.id = :playerId")
    Integer getTotalAssistsByPlayerId(@Param("playerId") Long playerId);

    @Query("SELECT SUM(g.pim) FROM GameStats g WHERE g.player.id = :playerId")
    Integer getTotalPimByPlayerId(@Param("playerId") Long playerId);
}