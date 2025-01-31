package com.example.GameData;

import com.example.GameData.model.Player;
import com.example.GameData.repository.PlayerRepository;
import com.example.GameData.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void savePlayer_ShouldSavePlayerSuccessfully() {
        Player inputPlayer = new Player("John", "Doe", 23);

        Player savedPlayer = new Player("John", "Doe", 23);
        savedPlayer.setId(1);

        when(playerRepository.save(any(Player.class))).thenReturn(savedPlayer);
        playerService.savePlayer(inputPlayer);
        verify(playerRepository, times(1)).save(inputPlayer);
    }

    @Test
    void deletePlayer_ShouldDeleteExistingPlayer() {
        // Arrange
        Integer playerId = 1;
        when(playerRepository.existsById(playerId)).thenReturn(true);

        // Act
        playerService.deletePlayer(playerId);

        // Assert
        verify(playerRepository).existsById(playerId);
        verify(playerRepository).deleteById(playerId);
    }
}